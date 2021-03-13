package edu.epam.project.controller.command.impl.hr;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;

public class ForwardToAddVacancyCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        return new CommandResult(PathJsp.CREATE_VACANCY_PAGE, TransitionType.FORWARD);
    }
}