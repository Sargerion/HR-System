package edu.epam.project.controller.command.impl.admin;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;

/**
 * The implementation of Command interface to forward on viewing all users page by admin user type.
 * @author Sargerion.
 */
public class ForwardToUserListCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        return new CommandResult(PathJsp.ALL_USERS, TransitionType.FORWARD);
    }
}