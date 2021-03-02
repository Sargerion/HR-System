package edu.epam.project.controller.command.impl.common;

import edu.epam.project.controller.command.*;
import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.UserType;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.service.UserService;
import edu.epam.project.model.service.impl.UserServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class LogInCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_LOGIN_PARAMETERS = "Empty login parameters";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        Optional<String> login = requestContext.getRequestParameter(RequestParameter.LOGIN);
        Optional<String> password = requestContext.getRequestParameter(RequestParameter.PASSWORD);
        Optional<String> userAvatar;
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
        Optional<String> correctLogin = Optional.empty();
        Map<Optional<User>, Map<Optional<String>, Optional<String>>> loginResult;
        try {
            if (!getLogin.isEmpty() && !getPassword.isEmpty()) {
                loginResult = userService.loginUser(getLogin, getPassword);
                for (Map.Entry<Optional<User>, Map<Optional<String>, Optional<String>>> entry : loginResult.entrySet()) {
                    optionalUser = entry.getKey();
                    for (Map.Entry<Optional<String>, Optional<String>> entryMessages : entry.getValue().entrySet()) {
                        optionalErrorMessage = entryMessages.getKey();
                        correctLogin = entryMessages.getValue();
                    }
                }
                correctLogin.ifPresent(s -> requestContext.setRequestAttribute(RequestAttribute.CORRECT_LOGIN, s));
                if (optionalErrorMessage.isPresent()) {
                    requestContext.setRequestAttribute(RequestAttribute.ERROR_MESSAGE, optionalErrorMessage.get());
                    commandResult = new CommandResult(PathJsp.LOGIN_PAGE, TransitionType.FORWARD);
                } else {
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        UserType userType = user.getType();
                        userAvatar = userService.findUserAvatar(user);
                        userAvatar.ifPresent(user::setAvatarName);
                        requestContext.setSessionAttribute(SessionAttribute.USER, user);
                        List<Specialty> specialties = userService.findAllSpecialties();
                        switch (userType) {
                            case ADMIN -> {
                                commandResult = new CommandResult(PathJsp.ADMIN_PAGE, TransitionType.FORWARD);
                                logger.info("Admin with login -> {} entered", user.getLogin());
                            }
                            case COMPANY_HR -> {
                                requestContext.setSessionAttribute(SessionAttribute.SPECIALTY_LIST, specialties);
                                commandResult = new CommandResult(PathJsp.HR_PAGE, TransitionType.FORWARD);
                                logger.info("Company HR with login -> {} entered", user.getLogin());
                            }
                            case FINDER -> {
                                requestContext.setSessionAttribute(SessionAttribute.SPECIALTY_LIST, specialties);
                                commandResult = new CommandResult(PathJsp.FINDER_PAGE, TransitionType.FORWARD);
                                logger.info("Finder with login -> {} entered", user.getLogin());
                            }
                        }
                    }
                }
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
        return commandResult;
    }
}