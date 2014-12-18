package org.aua.aoop.post.conf.ex;

/**
 * @author vahe.momjyan
 */
public class WrongKeyFormatException extends ConfigurationException {
    private static final String MESSAGE = "The provided property key/default key argument format is wrong";

    public WrongKeyFormatException() {
        super(MESSAGE);
    }

    public WrongKeyFormatException(String message) {
        super(message);
    }

    public WrongKeyFormatException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
