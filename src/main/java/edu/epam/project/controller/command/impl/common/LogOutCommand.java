package edu.epam.project.controller.command.impl.common;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.model.util.message.FriendlyMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The implementation of Command interface for logging out by common authorized user types.
 * @author Sargerion.
 */
public class LogOutCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        String locale = requestContext.getLocale();
        requestContext.setRequestAttribute(RequestAttribute.LOCALE, locale);
        logger.info(FriendlyMessage.LOG_OUT);
        return new CommandResult(PathJsp.HOME_PAGE, TransitionType.FORWARD);
    }
}