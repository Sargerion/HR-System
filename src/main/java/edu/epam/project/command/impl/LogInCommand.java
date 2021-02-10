package edu.epam.project.command.impl;

import edu.epam.project.command.Command;
import edu.epam.project.command.CommandResult;
import edu.epam.project.command.RequestParameter;
import edu.epam.project.command.SessionRequestContext;
import edu.epam.project.command.TransitionType;
import edu.epam.project.command.PathJsp;
import edu.epam.project.command.SessionAttribute;
import edu.epam.project.entity.User;
import edu.epam.project.entity.UserStatus;
import edu.epam.project.entity.UserType;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.service.UserService;
import edu.epam.project.service.impl.UserServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class LogInCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_LOGIN_PARAMETERS = "Empty sign up parameters";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        UserService userService = UserServiceImpl.INSTANCE;
        Optional<String> login = requestContext.getRequestParameter(RequestParameter.LOGIN);
        Optional<String> password = requestContext.getRequestParameter(RequestParameter.PASSWORD);
        String getLogin;
        String getPassword;
        if (login.isEmpty() || password.isEmpty()) {
            throw new CommandException(EMPTY_LOGIN_PARAMETERS);
        } else {
            getLogin = login.get();
            getPassword = password.get();
        }
        CommandResult commandResult = null;
        Optional<User> optionalUser = Optional.empty();
        try {
            optionalUser = userService.loginUser(getLogin, getPassword);
        } catch (ServiceException e) {
            String exception = e.toString();
            exception = exception.substring(exception.indexOf(":") + 1);
            requestContext.setRequestAttribute(RequestParameter.ERROR_MESSAGE, exception);
            commandResult = new CommandResult(PathJsp.LOGIN_PAGE, TransitionType.FORWARD);
            logger.error(e);
        }
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserType userType = user.getType();
            switch (userType) {
                case ADMIN -> {
                    requestContext.setSessionAttribute(SessionAttribute.USER_TYPE, SessionAttribute.ADMIN_TYPE);
                    requestContext.setSessionAttribute(SessionAttribute.USER, user);
                    commandResult = new CommandResult(PathJsp.ADMIN_PAGE, TransitionType.FORWARD);
                    logger.info("Admin with login -> {} entered", user.getLogin());
                }
                case COMPANY_HR -> {
                    requestContext.setSessionAttribute(SessionAttribute.USER_TYPE, SessionAttribute.HR_TYPE);
                    requestContext.setSessionAttribute(SessionAttribute.USER, user);
                    commandResult = new CommandResult(PathJsp.HR_PAGE, TransitionType.FORWARD);
                    logger.info("Company HR with login -> {} entered", user.getLogin());
                }
                case FINDER -> {
                    requestContext.setSessionAttribute(SessionAttribute.USER_TYPE, SessionAttribute.FINDER_TYPE);
                    requestContext.setSessionAttribute(SessionAttribute.USER, user);
                    commandResult = new CommandResult(PathJsp.FINDER_PAGE, TransitionType.FORWARD);
                    logger.info("Finder with login -> {} entered", user.getLogin());
                }
            }
        }
        return commandResult;
    }
}