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
        String getToChangeLocale;
        String getCurrentPage;
        if (toChangeLocale.isEmpty() || currentPage.isEmpty()) {
            throw new CommandException("Empty to change locale parameters");
        } else {
            getToChangeLocale = toChangeLocale.get();
            getCurrentPage = currentPage.get();
        }
        requestContext.setSessionAttribute(RequestParameter.LOCALE, getToChangeLocale);
        requestContext.setRequestAttribute(RequestParameter.CURRENT_PAGE, getCurrentPage);
        logger.info("Locale has changed:)");
        return new CommandResult(currentPage.get(), TransitionType.FORWARD);
    }
}