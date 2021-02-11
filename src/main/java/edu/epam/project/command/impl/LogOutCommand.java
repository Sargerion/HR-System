package edu.epam.project.command.impl;

import edu.epam.project.command.Command;
import edu.epam.project.command.CommandResult;
import edu.epam.project.command.RequestParameter;
import edu.epam.project.command.SessionRequestContext;
import edu.epam.project.command.TransitionType;
import edu.epam.project.command.PathJsp;

import edu.epam.project.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogOutCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        String locale = requestContext.getLocale();
        requestContext.setRequestAttribute(RequestParameter.LOCALE, locale);
        logger.info("Exit from account");
        return new CommandResult(PathJsp.HOME_PAGE, TransitionType.REDIRECT);
    }
}