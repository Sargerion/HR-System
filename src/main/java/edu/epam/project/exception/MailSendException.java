package edu.epam.project.exception;

/**
 * An exception to handle errors that occurred during the mail sending.
 * @author Sargerion.
 */
public class MailSendException extends Exception {

    public MailSendException() {
    }

    public MailSendException(String message) {
        super(message);
    }

    public MailSendException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailSendException(Throwable cause) {
        super(cause);
    }
}