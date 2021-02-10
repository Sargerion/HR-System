package edu.epam.project.command.impl;

import edu.epam.project.command.*;
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
    private static final String ERROR_LOGIN_MESSAGE = "Incorrect login or password";
    private static final String ERROR_STATUS_MESSAGE = "You account is not active";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        UserService userService = UserServiceImpl.INSTANCE;
        Optional<String> login = requestContext.getRequestParameter(RequestParameter.LOGIN);
        Optional<String> password = requestContext.getRequestParameter(RequestParameter.PASSWORD);
        String getLogin;
        String getPassword;
        if (login.isEmpty() || password.isEmpty()) {
            throw new CommandException("Empty sign up parameters");
        } else {
            getLogin = login.get();
            getPassword = password.get();
        }
        CommandResult commandResult = null;
        Optional<User> optionalUser = Optional.empty();
        try {
            optionalUser = userService.loginUser(getLogin, getPassword);
        } catch (ServiceException e) {
            requestContext.setRequestAttribute(RequestParameter.ERROR_MESSAGE, ERROR_STATUS_MESSAGE);
            commandResult = new CommandResult(PathJsp.LOGIN_PAGE, TransitionType.FORWARD);
        }
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getStatus() != UserStatus.BLOCKED) {
                UserType userType = user.getType();
                switch (userType) {
                    case ADMIN -> {
                        requestContext.setSessionAttribute(SessionAttribute.USER_TYPE, SessionAttribute.ADMIN_TYPE);
                        requestContext.setSessionAttribute(SessionAttribute.USER, user);
                        commandResult = new CommandResult(PathJsp.ADMIN_PAGE, TransitionType.FORWARD);
                        //comanres
                    }
                    case COMPANY_HR -> {
                        requestContext.setSessionAttribute(SessionAttribute.USER_TYPE, SessionAttribute.HR_TYPE);
                        requestContext.setSessionAttribute(SessionAttribute.USER, user);
                        commandResult = new CommandResult(PathJsp.ADMIN_PAGE, TransitionType.FORWARD);//TODO:ИЗМЕНИТЬ СТРАНИЦУ
                        //comanres
                    }
                    case FINDER -> {
                        requestContext.setSessionAttribute(SessionAttribute.USER_TYPE, SessionAttribute.FINDER_TYPE);
                        requestContext.setSessionAttribute(SessionAttribute.USER, user);
                        commandResult = new CommandResult(PathJsp.HOME_PAGE, TransitionType.FORWARD);
                        //comanres
                    }
                    default -> {
                        logger.error("Incorrect role");
                        throw new CommandException("Incorrect role");
                    }
                }
            } else {
                requestContext.setRequestAttribute(RequestParameter.ERROR_MESSAGE, ERROR_STATUS_MESSAGE);
                commandResult = new CommandResult(PathJsp.LOGIN_PAGE, TransitionType.FORWARD);
            }
        } else {
            requestContext.setRequestAttribute(RequestParameter.ERROR_MESSAGE, ERROR_LOGIN_MESSAGE);
            commandResult = new CommandResult(PathJsp.LOGIN_PAGE, TransitionType.FORWARD);
        }
        return commandResult;
    }
}