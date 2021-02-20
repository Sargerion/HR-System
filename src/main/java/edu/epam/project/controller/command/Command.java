package edu.epam.project.controller.command;

import edu.epam.project.exception.CommandException;
import edu.epam.project.model.entity.UserType;

public interface Command {

    CommandResult execute(SessionRequestContext requestContext) throws CommandException;

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