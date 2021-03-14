package edu.epam.project.exception;

/**
 * An exception to handle errors that occurred during the execution of the dao.
 * @author Sargerion.
 */
public class DaoException extends Exception{

    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
