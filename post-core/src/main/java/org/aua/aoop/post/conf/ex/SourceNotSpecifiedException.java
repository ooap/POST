package org.aua.aoop.post.conf.ex;

/**
 * @author vahe.momjyan
 */
public class SourceNotSpecifiedException extends ConfigurationException {
    private static final String MESSAGE = "Properties file source is not specified";

    public SourceNotSpecifiedException() {
        super(MESSAGE);
    }

    public SourceNotSpecifiedException(String message) {
        super(message);
    }

    public SourceNotSpecifiedException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
