package edu.epam.project.controller.command;

import edu.epam.project.exception.CommandException;
import edu.epam.project.model.entity.UserType;

/**
 * The interface Command.
 * @author Sargerion.
 */
public interface Command {

    /**
     * Processes user's request and returns page path with transition type.
     *
     * @param requestContext the request wrapper.
     * @return the command result which contains page path and transition type.
     * @throws CommandException when the services doesn't work correctly.
     */
    CommandResult execute(SessionRequestContext requestContext) throws CommandException;

    /**
     * Define home page for each user type.
     *
     * @param userType the user type.
     * @return the command result which contains page path and transition type.
     */
    default CommandResult defineCommandResult(UserType userType) {
        CommandResult commandResult;
        switch (userType) {
            case ADMIN -> {
                commandResult = new CommandResult(PathJsp.ADMIN_PAGE, TransitionType.FORWARD);
            }
            case COMPANY_HR -> {
                commandResult = new CommandResult(PathJsp.HR_PAGE, TransitionType.FORWARD);
            }
            case FINDER -> {
                commandResult = new CommandResult(PathJsp.FINDER_PAGE, TransitionType.FORWARD);
            }
            default -> {
                commandResult = new CommandResult(PathJsp.HOME_PAGE, TransitionType.FORWARD);
            }
        }
        return commandResult;
    }
}