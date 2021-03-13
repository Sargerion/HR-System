package edu.epam.project.controller.command.impl.admin;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;

public class ForwardToAddSpecialtyCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        return new CommandResult(PathJsp.ADD_SPECIALTY_PAGE, TransitionType.FORWARD);
    }
}