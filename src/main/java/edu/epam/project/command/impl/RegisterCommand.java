package edu.epam.project.command.impl;

import edu.epam.project.command.*;
import edu.epam.project.entity.User;
import edu.epam.project.exception.CommandException;

import edu.epam.project.exception.MailSendException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.service.UserService;
import edu.epam.project.service.impl.UserServiceImpl;
import edu.epam.project.service.impl.mail.MailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class RegisterCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_SIGN_UP_PARAMETERS = "Empty sign up parameters";
    private static final boolean IS_HR = true;
    private static final boolean NOT_HR = false;

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        Optional<String> login = requestContext.getRequestParameter(RequestParameter.LOGIN);
        Optional<String> password = requestContext.getRequestParameter(RequestParameter.PASSWORD);
        Optional<String> repeatPassword = requestContext.getRequestParameter(RequestParameter.REPEAT_PASSWORD);
        Optional<String> email = requestContext.getRequestParameter(RequestParameter.EMAIL);
        Optional<String> isHR = requestContext.getRequestParameter(RequestParameter.HR_OPTION_CHECK);
        String getLogin = "";
        String getPassword = "";
        String getRepeatPassword = "";
        String getEmail = "";
        CommandResult commandResult = null;
        if (login.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() || email.isEmpty()) {
            requestContext.setRequestAttribute(RequestAttribute.ERROR_MESSAGE, EMPTY_SIGN_UP_PARAMETERS);
            commandResult = new CommandResult(PathJsp.REGISTER_PAGE, TransitionType.FORWARD);
        } else {
            getLogin = login.get();
            getPassword = password.get();
            getRepeatPassword = repeatPassword.get();
            getEmail = email.get();
        }
        Optional<User> optionalUser = Optional.empty();
        Optional<String> optionalErrorMessage = Optional.empty();
        Map<Optional<User>, Optional<String>> registerResult;
        try {
            if (!getLogin.isEmpty() && !getPassword.isEmpty() && !getRepeatPassword.isEmpty() && !getEmail.isEmpty()) {
                if (isHR.isPresent()) {
                    registerResult = userService.registerUser(getLogin, getPassword, getRepeatPassword, getEmail, IS_HR);
                } else {
                    registerResult = userService.registerUser(getLogin, getPassword, getRepeatPassword, getEmail, NOT_HR);
                }
                for (Map.Entry<Optional<User>, Optional<String>> entry : registerResult.entrySet()) {
                    optionalUser = entry.getKey();
                    optionalErrorMessage = entry.getValue();
                }
                if (optionalUser.isPresent()) {
                    MailSender mailSender = MailSender.getInstance();
                    if (isHR.isEmpty()) {
                        mailSender.sendActivationFinder(optionalUser.get());
                        requestContext.setRequestAttribute(RequestAttribute.CONFIRM_MESSAGE, FriendlyMessage.CONFIRM_REGISTER_MESSAGE_FINDER);
                    } else {
                        mailSender.sendNotificationToHR(optionalUser.get());
                        requestContext.setRequestAttribute(RequestAttribute.CONFIRM_MESSAGE, FriendlyMessage.REGISTER_MESSAGE_HR);
                    }
                    commandResult = new CommandResult(PathJsp.HOME_PAGE, TransitionType.FORWARD);
                }
                if (optionalErrorMessage.isPresent()) {
                    requestContext.setRequestAttribute(RequestAttribute.ERROR_MESSAGE, optionalErrorMessage.get());
                    commandResult = new CommandResult(PathJsp.REGISTER_PAGE, TransitionType.FORWARD);
                }
            }
        } catch (ServiceException | MailSendException e) {
            logger.error(e);
            throw new CommandException(e);
        }
        return commandResult;
    }
}