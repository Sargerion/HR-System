package edu.epam.project.controller.command.impl.finder;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Application;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.service.FinderService;
import edu.epam.project.model.service.impl.FinderServiceImpl;
import edu.epam.project.model.util.message.ErrorMessage;
import edu.epam.project.model.util.message.FriendlyMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class ApplyVacancyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final boolean IS_CONFIRM_APPLICATION = false;
    private static final String EMPTY_APPLY_VACANCY_PARAMETERS = "Empty apply vacancy parameters";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        FinderService finderService = FinderServiceImpl.getInstance();
        User user = (User) requestContext.getSessionAttribute(SessionAttribute.USER);
        Optional<String> vacancyId = requestContext.getRequestParameter(RequestParameter.APPLY_VACANCY_BUTTON);
        Integer finderId = user.getEntityId();
        CommandResult commandResult = new CommandResult(PathJsp.VACANCIES_VIEW_PAGE, TransitionType.REDIRECT);
        if (vacancyId.isEmpty()) {
            requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, EMPTY_APPLY_VACANCY_PARAMETERS);
        } else {
            try {
                if (finderService.existsFinder(finderId)) {
                    Optional<Application> application = Optional.empty();
                    Optional<String> errorMessage = Optional.empty();
                    Map<Optional<Application>, Optional<String>> applyResult;
                    applyResult = finderService.buildApplication(vacancyId.get(), finderId, IS_CONFIRM_APPLICATION);
                    for (Map.Entry<Optional<Application>, Optional<String>> entry : applyResult.entrySet()) {
                        application = entry.getKey();
                        errorMessage = entry.getValue();
                    }
                    if (errorMessage.isPresent()) {
                        requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, errorMessage.get());
                    } else {
                        if (application.isPresent()) {
                            requestContext.setSessionAttribute(SessionAttribute.CONFIRM_MESSAGE, FriendlyMessage.APPLY_FINDER);
                        }
                    }
                } else {
                    commandResult = new CommandResult(PathJsp.ADD_FINDER_INFO_PAGE, TransitionType.REDIRECT);
                    requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, ErrorMessage.FINDER_WITHOUT_INFO);
                }
            } catch (ServiceException e) {
                logger.error(e);
                throw new CommandException(e);
            }
        }
        return commandResult;
    }
}