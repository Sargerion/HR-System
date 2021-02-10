package edu.epam.project.command.impl;

import edu.epam.project.command.*;
import edu.epam.project.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        String locale = requestContext.getLocale();
        requestContext.setRequestAttribute(RequestParameter.LOCALE, locale);
        return new CommandResult(PathJsp.HOME_PAGE, TransitionType.FORWARD);
    }
}