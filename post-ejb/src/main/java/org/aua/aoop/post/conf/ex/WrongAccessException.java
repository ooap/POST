package org.aua.aoop.post.conf.ex;

/**
 * @author vahe.momjyan
 */
public class WrongAccessException extends ConfigurationException {
    private static final String MESSAGE = "A property must be declared non-constant.";

    public WrongAccessException() {
        super(MESSAGE);
    }

    public WrongAccessException(String message) {
        super(message);
    }

    public WrongAccessException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
