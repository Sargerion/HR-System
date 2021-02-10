package edu.epam.project.command.impl;

import edu.epam.project.command.*;
import edu.epam.project.entity.User;
import edu.epam.project.entity.UserType;
import edu.epam.project.exception.CommandException;

import edu.epam.project.exception.MailSendException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.service.UserService;
import edu.epam.project.service.impl.UserServiceImpl;
import edu.epam.project.util.mail.MailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class RegisterCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_SIGN_UP_PARAMETERS = "Empty sign up parameters";
    private static final boolean IS_HR = true;
    private static final boolean NOT_HR = false;

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        UserService userService = UserServiceImpl.INSTANCE;
        Optional<String> login = requestContext.getRequestParameter(RequestParameter.LOGIN);
        Optional<String> password = requestContext.getRequestParameter(RequestParameter.PASSWORD);
        Optional<String> repeatPassword = requestContext.getRequestParameter(RequestParameter.REPEAT_PASSWORD);
        Optional<String> email = requestContext.getRequestParameter(RequestParameter.EMAIL);
        Optional<String> isHR = requestContext.getRequestParameter(RequestParameter.HR_OPTION_CHECK);
        String getLogin;
        String getPassword;
        String getRepeatPassword;
        String getEmail;
        if (login.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() || email.isEmpty()) {
            throw new CommandException(EMPTY_SIGN_UP_PARAMETERS);
        } else {
            getLogin = login.get();
            getPassword = password.get();
            getRepeatPassword = repeatPassword.get();
            getEmail = email.get();
        }
        CommandResult commandResult = null;
        Optional<User> optionalUser = Optional.empty();
        try {
            if (isHR.isPresent()) {
                optionalUser = userService.registerUser(getLogin, getPassword, getRepeatPassword, getEmail, IS_HR);
            } else {
                optionalUser = userService.registerUser(getLogin, getPassword, getRepeatPassword, getEmail, NOT_HR);
            }
        } catch (ServiceException e) {
            String exception = e.toString();
            exception = exception.substring(exception.indexOf(":") + 1);
            requestContext.setRequestAttribute(RequestParameter.ERROR_MESSAGE, exception);
            commandResult = new CommandResult(PathJsp.REGISTER_PAGE, TransitionType.FORWARD);
            logger.error(e);
        }
        try {
            if (optionalUser.isPresent()) {
                if (isHR.isEmpty()) {
                    MailSender mailSender = new MailSender(optionalUser.get().getEmail());
                    mailSender.sendActivationFinder(optionalUser.get());
                    commandResult = new CommandResult(PathJsp.HOME_PAGE, TransitionType.FORWARD);
                    requestContext.setRequestAttribute(RequestParameter.CONFIRM_MESSAGE, FriendlyMessage.CONFIRM_REGISTER_MESSAGE_FINDER);
                } else {
                    MailSender mailSender = new MailSender(optionalUser.get().getEmail());
                    mailSender.sendNotificationToHR(optionalUser.get());
                    commandResult = new CommandResult(PathJsp.HOME_PAGE, TransitionType.FORWARD);
                    requestContext.setRequestAttribute(RequestParameter.CONFIRM_MESSAGE, FriendlyMessage.REGISTER_MESSAGE_HR);
                }
            }
        } catch (MailSendException e) {
            throw new CommandException(e);
        }
        return commandResult;
    }
}