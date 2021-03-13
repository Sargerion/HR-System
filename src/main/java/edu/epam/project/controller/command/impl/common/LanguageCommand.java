package edu.epam.project.controller.command.impl.common;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.UserType;
import edu.epam.project.model.util.message.FriendlyMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class LanguageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_TO_CHANGE_LOCALE_PARAMETERS = "You didn't pass the parameters to change locale";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        Optional<String> toChangeLocale = requestContext.getRequestParameter(RequestParameter.LOCALE);
        Optional<String> currentPage = requestContext.getRequestParameter(RequestParameter.CURRENT_PAGE);
        User user = (User) requestContext.getSessionAttribute(SessionAttribute.USER);
        UserType userType = UserType.GUEST;
        CommandResult commandResult;
        if (user != null) {
            userType = user.getType();
        }
        if (toChangeLocale.isEmpty() || currentPage.isEmpty()) {
            requestContext.setRequestAttribute(RequestAttribute.ERROR_MESSAGE, EMPTY_TO_CHANGE_LOCALE_PARAMETERS);
            commandResult = defineCommandResult(userType);
        } else {
            requestContext.setSessionAttribute(SessionAttribute.LOCALE, toChangeLocale.get());
            commandResult = new CommandResult(currentPage.get(), TransitionType.FORWARD);
            logger.info(FriendlyMessage.LOCALE_CHANGED);
        }
        return commandResult;
    }
}