package edu.epam.project.controller.command.impl.common;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;

/**
 * The implementation of Command interface to forward on changing avatar page by common authorized user types.
 * @author Sargerion.
 */
public class ForwardToChangeAvatarCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        return new CommandResult(PathJsp.CHANGE_AVATAR_PAGE, TransitionType.FORWARD);
    }
}