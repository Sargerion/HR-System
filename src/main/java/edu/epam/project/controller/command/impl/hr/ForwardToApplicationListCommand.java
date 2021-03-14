package edu.epam.project.controller.command.impl.hr;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;

/**
 * The implementation of Command interface to forward on viewing application list page by company_hr user type.
 * @author Sargerion.
 */
public class ForwardToApplicationListCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        return new CommandResult(PathJsp.APPLICATIONS_VIEW_PAGE, TransitionType.FORWARD);
    }
}