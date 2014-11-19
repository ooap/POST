package org.aua.aoop.post.conf;

import org.aua.aoop.post.conf.annotation.PropertiesSource;
import org.aua.aoop.post.conf.annotation.Value;

/**
 * <p></p>Service Configuration Placeholder</p>
 * <p/>
 *
 * @author vahe.momjyan
 * @see AbstractConfigurationPlaceholder
 */
@PropertiesSource("config.properties")
public class ClinetConfig extends AbstractConfigurationPlaceholder {
    private static ClinetConfig INSTANCE = new ClinetConfig();

    // Database Configuration
    @Value(key = "#{org.aua.aoop.post.ejb.module.name:post-ejb}")
    String ejbModuleName;

    public String getEjbModuleName() {
        return ejbModuleName;
    }

    public static ClinetConfig getInstance() {
        return INSTANCE;
    }
}
