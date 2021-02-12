package edu.epam.project.command.impl;

import edu.epam.project.command.*;

import edu.epam.project.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class LanguageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        Optional<String> toChangeLocale = requestContext.getRequestParameter(RequestParameter.LOCALE);
        Optional<String> currentPage = requestContext.getRequestParameter(RequestParameter.CURRENT_PAGE);
        requestContext.setSessionAttribute(SessionAttribute.LOCALE, toChangeLocale.get());
        requestContext.setRequestAttribute(RequestAttribute.CURRENT_PAGE, currentPage.get());
        logger.info(FriendlyMessage.LOCALE_CHANGED);
        return new CommandResult(currentPage.get(), TransitionType.FORWARD);
    }
}