package edu.epam.project.controller.command.impl.finder;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;

/**
 * The implementation of Command interface to forward on editing info page by finder user type.
 * @author Sargerion.
 */
public class ForwardToEditInfoCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        return new CommandResult(PathJsp.EDIT_FINDER_INFO_PAGE, TransitionType.FORWARD);
    }
}