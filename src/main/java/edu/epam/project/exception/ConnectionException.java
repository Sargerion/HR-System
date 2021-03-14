package edu.epam.project.exception;

/**
 * An exception to handle errors that occurred during the execution of the custom connection pool.
 * @author Sargerion.
 */
public class ConnectionException extends Exception {

    public ConnectionException() {
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionException(Throwable cause) {
        super(cause);
    }
}