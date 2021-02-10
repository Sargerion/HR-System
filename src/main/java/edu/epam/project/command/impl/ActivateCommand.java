package edu.epam.project.command.impl;

import edu.epam.project.command.*;
import edu.epam.project.entity.User;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.service.UserService;
import edu.epam.project.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Optional;

public class ActivateCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String CORRECT_ACTIVATE = "Congrats, you've successfully confirm registration, now you're active";
    private static final String ERROR_EXPIRE_TIME = "It's a pity, but confirmation time expired, please register anew!";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        //todo:УДАЛИТЬ ЮЗЕРА
        UserService userService = UserServiceImpl.INSTANCE;
        Optional<String> confirm_Token = requestContext.getRequestParameter(RequestParameter.CONFIRMATION_TOKEN);
        Optional<String> userId = requestContext.getRequestParameter(RequestParameter.USER_ID);
        Optional<User> activatingUser;
        CommandResult commandResult = null;
        if (confirm_Token.isEmpty() || userId.isEmpty()) {
            requestContext.setRequestAttribute(RequestParameter.ERROR_MESSAGE, ERROR_EXPIRE_TIME);
            commandResult = new CommandResult(PathJsp.HOME_PAGE, TransitionType.FORWARD);
            throw new CommandException("Empty Parameters");
        }
        try {
            activatingUser = userService.findById(Integer.parseInt(userId.get()));
            if (activatingUser.isPresent()) {
                logger.info(activatingUser.get().getConfirmationToken());
                Optional<String> token = userService.findUserActivateTokenById(Integer.parseInt(userId.get()));
                activatingUser.get().setConfirmationToken(token.get());
                if (userService.activateUser(activatingUser.get(), confirm_Token.get())) {
                    requestContext.setRequestAttribute(RequestParameter.CONFIRM_MESSAGE, CORRECT_ACTIVATE);
                    commandResult = new CommandResult(PathJsp.LOGIN_PAGE, TransitionType.REDIRECT);
                    activatingUser.get().setConfirmationToken(null);
                    //todo:update user
                } else {
                    requestContext.setRequestAttribute(RequestParameter.ERROR_MESSAGE, ERROR_EXPIRE_TIME);
                    commandResult = new CommandResult(PathJsp.LOGIN_PAGE, TransitionType.FORWARD);
                    activatingUser.get().setConfirmationToken(null);
                    //todo:update user
                }
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return commandResult;
    }
}