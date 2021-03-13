package edu.epam.project.controller.command.impl.common;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;

public class ForwardToChangeAvatarCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        return new CommandResult(PathJsp.CHANGE_AVATAR_PAGE, TransitionType.FORWARD);
    }
}