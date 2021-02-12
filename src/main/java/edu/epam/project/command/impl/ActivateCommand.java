package edu.epam.project.command.impl;

import edu.epam.project.command.*;
import edu.epam.project.entity.User;
import edu.epam.project.entity.UserStatus;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ExceptionMessage;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.service.UserService;
import edu.epam.project.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Optional;

public class ActivateCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String TOKEN_EXPIRE = "confirmed";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        Optional<String> userId = requestContext.getRequestParameter(RequestParameter.USER_ID);
        Optional<String> userConfirmationToken = requestContext.getRequestParameter(RequestParameter.CONFIRMATION_TOKEN);
        Optional<User> activatingUser;
        CommandResult commandResult = null;
        try {
            activatingUser = userService.findById(Integer.parseInt(userId.get()));
            if (activatingUser.isPresent()) {
                String currentToken = activatingUser.get().getConfirmationToken();
                if (!currentToken.equals(userConfirmationToken.get())) {
                    requestContext.setRequestAttribute(RequestAttribute.ERROR_MESSAGE, ExceptionMessage.ERROR_SECOND_TRANSIT_BY_LINK);
                    commandResult = new CommandResult(PathJsp.HOME_PAGE, TransitionType.FORWARD);
                    logger.error(ExceptionMessage.ERROR_SECOND_TRANSIT_BY_LINK);
                } else {
                    requestContext.setRequestAttribute(RequestAttribute.CONFIRM_MAIL_MESSAGE, FriendlyMessage.CORRECT_ACTIVATE_FINDER);
                    commandResult = new CommandResult(PathJsp.LOGIN_PAGE, TransitionType.FORWARD);
                    activatingUser.get().setConfirmationToken(TOKEN_EXPIRE);
                    activatingUser.get().setStatus(UserStatus.ACTIVE);
                    userService.update(activatingUser.get());
                    logger.info(FriendlyMessage.CORRECT_ACTIVATE_FINDER);
                }
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
        return commandResult;
    }
}