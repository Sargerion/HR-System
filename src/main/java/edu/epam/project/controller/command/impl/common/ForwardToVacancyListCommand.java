package edu.epam.project.controller.command.impl.common;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;

/**
 * The implementation of Command interface to forward on vacancy list viewing page by common authorized user types.
 * @author Sargerion.
 */
public class ForwardToVacancyListCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        return new CommandResult(PathJsp.VACANCIES_VIEW_PAGE, TransitionType.FORWARD);
    }
}