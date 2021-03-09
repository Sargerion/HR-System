package edu.epam.project.controller.command.impl.admin;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.service.UserService;
import edu.epam.project.model.service.impl.UserServiceImpl;
import edu.epam.project.model.util.message.FriendlyMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UnblockUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_UNBLOCK_PARAMETERS = "Empty unblock user parameters";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        Optional<String> userId = requestContext.getRequestParameter(RequestParameter.UNBLOCK_USER_BUTTON);
        CommandResult commandResult = new CommandResult(PathJsp.ALL_USERS, TransitionType.REDIRECT);
        if (userId.isEmpty()) {
            requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, EMPTY_UNBLOCK_PARAMETERS);
        } else {
            Optional<String> errorMessage;
            try {
                errorMessage = userService.unblockUser(userId.get());
                if (errorMessage.isEmpty()) {
                    requestContext.setSessionAttribute(SessionAttribute.CONFIRM_MESSAGE, FriendlyMessage.UNBLOCK_USER_CORRECT);
                } else {
                    requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, errorMessage.get());
                }
            } catch (ServiceException e) {
                logger.error(e);
                throw new CommandException(e);
            }
        }
        return commandResult;
    }
}