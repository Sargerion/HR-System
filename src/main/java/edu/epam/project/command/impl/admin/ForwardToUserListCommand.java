package edu.epam.project.command.impl.admin;

import edu.epam.project.command.*;
import edu.epam.project.exception.CommandException;

public class ForwardToUserListCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        return new CommandResult(PathJsp.ALL_USERS, TransitionType.FORWARD);
    }
}