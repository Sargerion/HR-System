package edu.epam.project.controller.command.impl.hr;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;

/**
 * The implementation of Command interface to forward on adding vacancy page by company_hr user type.
 * @author Sargerion.
 */
public class ForwardToAddVacancyCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        return new CommandResult(PathJsp.CREATE_VACANCY_PAGE, TransitionType.FORWARD);
    }
}