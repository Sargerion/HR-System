package edu.epam.project.command.impl;

import edu.epam.project.command.*;
import edu.epam.project.entity.User;
import edu.epam.project.entity.UserType;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.service.UserService;
import edu.epam.project.service.impl.UserServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.KeyStore;
import java.util.*;

public class LogInCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_LOGIN_PARAMETERS = "Empty login parameters";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        Optional<String> login = requestContext.getRequestParameter(RequestParameter.LOGIN);
        Optional<String> password = requestContext.getRequestParameter(RequestParameter.PASSWORD);
        String getLogin = "";
        String getPassword = "";
        CommandResult commandResult = null;
        if (login.isEmpty() || password.isEmpty()) {
            requestContext.setRequestAttribute(RequestAttribute.ERROR_MESSAGE, EMPTY_LOGIN_PARAMETERS);
            commandResult = new CommandResult(PathJsp.LOGIN_PAGE, TransitionType.FORWARD);
        } else {
            getLogin = login.get();
            getPassword = password.get();
        }
        Optional<User> optionalUser = Optional.empty();
        Optional<String> optionalErrorMessage = Optional.empty();
        Map<Optional<User>, Optional<String>> loginResult;
        try {
            if(!getLogin.isEmpty() && !getPassword.isEmpty()) {
                loginResult = userService.loginUser(getLogin, getPassword);
                for (Map.Entry<Optional<User>, Optional<String>> entry : loginResult.entrySet()) {
                    optionalUser = entry.getKey();
                    optionalErrorMessage = entry.getValue();
                }
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    UserType userType = user.getType();
                    requestContext.setSessionAttribute(SessionAttribute.USER, user);
                    switch (userType) {
                        case ADMIN -> {
                            commandResult = new CommandResult(PathJsp.ADMIN_PAGE, TransitionType.FORWARD);
                            logger.info("Admin with login -> {} entered", user.getLogin());
                        }
                        case COMPANY_HR -> {
                            commandResult = new CommandResult(PathJsp.HR_PAGE, TransitionType.FORWARD);
                            logger.info("Company HR with login -> {} entered", user.getLogin());
                        }
                        case FINDER -> {
                            commandResult = new CommandResult(PathJsp.FINDER_PAGE, TransitionType.FORWARD);
                            logger.info("Finder with login -> {} entered", user.getLogin());
                        }
                    }
                }
                if (optionalErrorMessage.isPresent()) {
                    requestContext.setRequestAttribute(RequestAttribute.ERROR_MESSAGE, optionalErrorMessage.get());
                    commandResult = new CommandResult(PathJsp.LOGIN_PAGE, TransitionType.FORWARD);
                }
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
        return commandResult;
    }
}