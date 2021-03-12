package edu.epam.project.controller.command.impl.hr;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Finder;
import edu.epam.project.model.service.FinderService;
import edu.epam.project.model.service.impl.FinderServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class ViewFinderProfile implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_ACTIVATE_PARAMETERS = "Empty activate parameters";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        FinderService finderService = FinderServiceImpl.getInstance();
        CommandResult commandResult = new CommandResult(PathJsp.VIEW_FINDER_PROFILE, TransitionType.FORWARD);
        Optional<String> finderId = requestContext.getRequestParameter(RequestParameter.FINDER_BUTTON);
        if (finderId.isEmpty()) {
            requestContext.setRequestAttribute(RequestAttribute.ERROR_MESSAGE, EMPTY_ACTIVATE_PARAMETERS);
        } else {
            Optional<Finder> finder = Optional.empty();
            Optional<String> errorMessage = Optional.empty();
            Map<Optional<Finder>, Optional<String>> findResult;
            try {
                findResult = finderService.findFinderById(finderId.get());
                for (Map.Entry<Optional<Finder>, Optional<String>> entry : findResult.entrySet()) {
                    finder = entry.getKey();
                    errorMessage = entry.getValue();
                }
                if (errorMessage.isPresent()) {
                    requestContext.setRequestAttribute(RequestAttribute.ERROR_MESSAGE, errorMessage.get());
                } else {
                    if (finder.isPresent()) {
                        Optional<String> finderLogin = finderService.findFinderLogin(finder.get().getEntityId());
                        requestContext.setSessionAttribute(SessionAttribute.VIEWING_BY_HR_FINDER, finder.get());
                        finderLogin.ifPresent(s -> requestContext.setSessionAttribute(SessionAttribute.FINDER_LOGIN, s));
                    }
                }
            } catch (ServiceException e) {
                logger.error(e);
                throw new CommandException(e);
            }
        }
        return commandResult;
    }
}