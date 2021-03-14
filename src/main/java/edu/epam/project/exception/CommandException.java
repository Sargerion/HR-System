package edu.epam.project.exception;

/**
 * An exception to handle errors that occurred during the execution of the command.
 * @author Sargerion.
 */
public class CommandException extends Exception {
    public CommandException() {
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }
}