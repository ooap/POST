package org.aua.aoop.post.conf;

import org.aua.aoop.post.conf.annotation.PropertiesSource;
import org.aua.aoop.post.conf.annotation.Value;
import org.aua.aoop.post.conf.ex.ConfigurationException;
import org.aua.aoop.post.conf.ex.SourceNotSpecifiedException;
import org.aua.aoop.post.conf.ex.WrongAccessException;
import org.aua.aoop.post.conf.ex.WrongKeyFormatException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.AbstractMap;
import java.util.Properties;

/**
 * <p>Analyzes the annotations of the derived class and initializes the property values
 * based on the provided annotation values.</p>
 * <p/>
 * <p>Each derived class must be annotated with a {@link org.aua.aoop.post.conf.annotation.PropertiesSource}
 * annotation and specify a target properties file to be loaded.
 * They must also implement the singleton pattern.</p>
 * <p/>
 * <p>Every non-constant {@link String} field of the derived class annotated with {@link Value}
 * will be initialized with the values from the provided properties file. If the key does not appear
 * in the properties file then the field will be initialized with the default value.</p>
 * <p/>
 * <p>The format of the key specification of the {@link Value} is as follows:
 * <p/>
 * #{{@code key}[:{@code defaultValue}]}
 * <p/>
 * The square brackets mean that the defaultValue is optional and must not be included in the specification.</p>
 * <p/>
 * <p>The class field might also be annotated with {@link Value} and its defaultValue field.
 * That will look like this:
 * <p/>
 * {@code
 *
 * @author vahe.momjyan
 * @Value(key = "key", defaultValue = "defaultValue")
 * class ...
 * }
 * </p>
 * <p/>
 * <p>If the default values are specified in both way, the definition in the key specification
 * will override the other one.</p>
 * <p/>
 * <p>If no default value is defined and empty string will be returned as a value.</p>
 * @see Value
 * @see PropertiesSource
 */
public abstract class AbstractConfigurationPlaceholder {

    /**
     * Loads the properties file and initializes the class fields based on the loaded file.
     *
     * @throws SourceNotSpecifiedException occurs when the extending class is not annotated
     *                                     with the {@link PropertiesSource} annotation.
     * @throws java.io.IOException                 occurs when the file specified in the {@link PropertiesSource}
     *                                     annotation does not exist or cannot be opened.
     * @throws WrongAccessException        occurs when the annotated field is either declared final
     *                                     or is not of type {@link String}.
     * @throws WrongKeyFormatException     occurs if the key specified in the {@link Value} annotation
     *                                     does not follow the defined format.
     */
    protected AbstractConfigurationPlaceholder() {

        Class<? extends AbstractConfigurationPlaceholder> c = getClass();
        PropertiesSource propertiesSourceAnnotation;

        if (!c.isAnnotationPresent(PropertiesSource.class)) {
            throw new SourceNotSpecifiedException();
        }

        propertiesSourceAnnotation = c.getAnnotation(PropertiesSource.class);

        String propertiesFilePath;
        propertiesFilePath = propertiesSourceAnnotation.value();

        InputStream file;
        Properties properties = new Properties();
        try {
            URL resource = getClass().getResource("/" + propertiesFilePath);

            if (resource == null) {
                //SystemLogger.error("Properties file {} not found for class {}.", propertiesFilePath, c.getName());
                throw new IOException("File not found: " + propertiesFilePath);
            }

            file = resource.openStream();
            properties.load(file);
            file.close();
        } catch (IOException e) {
            throw new ConfigurationException(e.getMessage(), e);
        }


        Field[] fields = c.getDeclaredFields();

        for (Field field : fields) {
            initializeField(field, properties);
        }
    }

    private void initializeField(Field field, Properties properties) {
        if (!field.isAnnotationPresent(Value.class)) {
            return;
        }

        validateField(field);
        Object value = obtainPropertyValue(field, properties);
        assignPropertyValue(field, value);
    }

    private void assignPropertyValue(Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(this, value);
        } catch (IllegalAccessException e1) {
            throw new WrongAccessException(e1);
        }
    }

    private Object obtainPropertyValue(Field field, Properties properties) {
        Value valueAnnotation = field.getAnnotation(Value.class);
        AbstractMap.SimpleEntry<String, String> decapsulatedValues = getPropertyDefault(valueAnnotation.key());

        String key = decapsulatedValues.getKey();
        String defaultValue = decapsulatedValues.getValue();

        if (defaultValue == null) {
            defaultValue = valueAnnotation.defaultValue();
        }

        Object value = castFieldValue(field, (String) properties.get(key));

        if (value == null) {
            value = castFieldValue(field, defaultValue);
        }

        return value;
    }

    private Object castFieldValue(Field field, String value) {
        Object result = null;

        if (value != null) {
            Type type = field.getType();

            try {
                if (type.equals(String.class)) {
                    result = value;
                } else if (type.equals(Integer.class)) {
                    result = Integer.parseInt(value);
                } else if (type.equals(Double.class)) {
                    result = Double.parseDouble(value);
                } else if (type.equals(Long.class)) {
                    result = Long.parseLong(value);
                } else if (type.equals(Boolean.class)) {
                    result = Boolean.parseBoolean(value);
                } else {
                    //SystemLogger.error("The declared type of '{}' field is not supported ({})", field.getName(), field.getType().getSimpleName());
                    throw new WrongAccessException();
                }
            } catch (NumberFormatException ex) {
                //SystemLogger.error("Could not parse value of '{}' field", field.getName());
                throw new WrongAccessException();
            }
        }
        return result;
    }

    private void validateField(Field field) {
        if (Modifier.isFinal(field.getModifiers())) {
            //SystemLogger.error("Seems like the '{}' field of is final" +
            //        "while annotated as @Value.", field.getName());
            throw new WrongAccessException();
        }
    }

    private AbstractMap.SimpleEntry<String, String> getPropertyDefault(String encapsulatedValue) {

        // We expect input of "#{key:defaultValue}"
        // We must remove the first two characters as well as the last one.
        String decapsulatedValue = encapsulatedValue.substring(2).substring(0, encapsulatedValue.length() - 3);
        // The following complicated call chain will solve the problem.
        String[] split = decapsulatedValue.split(":");

        // TODO: check with regex (#\{[^:]*}|#\{[^:]*:[^:]*\})

        if (split.length > 2) {
            throw new WrongKeyFormatException();
        }

        String key = split[0];
        String defaultValue = null;

        if (split.length == 2) {
            defaultValue = split[1];
        }

        return new AbstractMap.SimpleEntry<String, String>(key, defaultValue);
    }
}
