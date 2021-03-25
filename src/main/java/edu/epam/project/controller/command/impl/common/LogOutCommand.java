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
    private static final boolean INVALIDATE_TRUE = true;

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        requestContext.setLogout(INVALIDATE_TRUE);
        logger.info(FriendlyMessage.LOG_OUT);
        return new CommandResult(PathJsp.HOME_PAGE, TransitionType.FORWARD);
    }
}