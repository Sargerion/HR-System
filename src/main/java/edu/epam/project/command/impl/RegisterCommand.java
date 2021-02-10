package edu.epam.project.command.impl;

import edu.epam.project.command.*;
import edu.epam.project.entity.User;
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
    private static final String CONFIRM_REGISTER_MESSAGE = "Registration complete, please check confirmation link in the mail";
    private static final String TEMP_ERROR = "Error data";
    private static final boolean IS_HR = true;
    private static final boolean NOT_HR = true;

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
            throw new CommandException("Empty sign up parameters");
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
            if (optionalUser.isPresent()) {
                MailSender mailSender = new MailSender(optionalUser.get().getEmail());
                mailSender.sendActivation(optionalUser.get());
                commandResult = new CommandResult(PathJsp.HOME_PAGE, TransitionType.FORWARD);
                requestContext.setRequestAttribute(RequestParameter.CONFIRM_MESSAGE, CONFIRM_REGISTER_MESSAGE);
            }
        } catch (ServiceException e) {
            requestContext.setRequestAttribute(RequestParameter.ERROR_MESSAGE, TEMP_ERROR);
            commandResult = new CommandResult(PathJsp.REGISTER_PAGE, TransitionType.FORWARD);
        } catch (MailSendException e) {
            throw new CommandException(e);
        }
        return commandResult;
    }
}