package edu.epam.project.exception;

/**
 * An exception to handle errors that occurred during the execution of the service.
 * @author Sargerion.
 */
public class ServiceException extends Exception {

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
