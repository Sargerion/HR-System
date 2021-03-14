package edu.epam.project.controller.command.impl.admin;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;

/**
 * The implementation of Command interface to forward on adding company page by admin user type.
 * @author Sargerion.
 */
public class ForwardToAddCompanyCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        return new CommandResult(PathJsp.ADD_COMPANY_PAGE, TransitionType.FORWARD);
    }
}