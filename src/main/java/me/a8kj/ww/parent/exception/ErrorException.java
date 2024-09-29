package me.a8kj.ww.parent.exception;

/**
 * Custom exception class to represent errors within the event system.
 */
public class ErrorException extends Exception {
    private final Throwable cause;

    /**
     * Constructs a new ErrorException based on the given Throwable.
     *
     * @param throwable The throwable that triggered this exception.
     */
    public ErrorException(Throwable throwable) {
        this.cause = throwable;
    }

    /**
     * Constructs a new ErrorException without a cause.
     */
    public ErrorException() {
        this.cause = null;
    }

    /**
     * Constructs a new ErrorException with the given message and cause.
     *
     * @param cause   The throwable that caused this exception.
     * @param message The detail message for this exception.
     */
    public ErrorException(Throwable cause, String message) {
        super(message);
        this.cause = cause;
    }

    /**
     * Constructs a new ErrorException with the given message.
     *
     * @param message The detail message for this exception.
     */
    public ErrorException(String message) {
        super(message);
        this.cause = null;
    }

    /**
     * Returns the throwable that triggered this exception, if applicable.
     *
     * @return The inner throwable, or null if none exists.
     */
    @Override
    public Throwable getCause() {
        return cause;
    }
}
