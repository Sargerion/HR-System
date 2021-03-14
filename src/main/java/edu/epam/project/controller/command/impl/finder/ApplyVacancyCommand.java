package edu.epam.project.controller.command.impl.finder;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Application;
import edu.epam.project.model.entity.Finder;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.service.ApplicationService;
import edu.epam.project.model.service.FinderService;
import edu.epam.project.model.service.impl.ApplicationServiceImpl;
import edu.epam.project.model.service.impl.FinderServiceImpl;
import edu.epam.project.model.util.message.ErrorMessage;
import edu.epam.project.model.util.message.FriendlyMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

/**
 * The implementation of Command interface for vacancy applying by finder user type.
 * @author Sargerion.
 */
public class ApplyVacancyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_APPLY_VACANCY_PARAMETERS = "Empty apply vacancy parameters";
    private static final String FINDER_NOT_HIRE_STATUS = "Not Hire";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        FinderService finderService = FinderServiceImpl.getInstance();
        ApplicationService applicationService = ApplicationServiceImpl.getInstance();
        User user = (User) requestContext.getSessionAttribute(SessionAttribute.USER);
        Optional<String> vacancyId = requestContext.getRequestParameter(RequestParameter.APPLY_VACANCY_BUTTON);
        Integer finderId = user.getEntityId();
        CommandResult commandResult = new CommandResult(PathJsp.VACANCIES_VIEW_PAGE, TransitionType.REDIRECT);
        if (vacancyId.isEmpty()) {
            requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, EMPTY_APPLY_VACANCY_PARAMETERS);
        } else {
            try {
                if (finderService.existsFinder(finderId)) {
                    Finder finder = finderService.findById(finderId).get();
                    if (finder.getWorkStatus().equals(FINDER_NOT_HIRE_STATUS)) {
                        Optional<Application> application = Optional.empty();
                        Optional<String> errorMessage = Optional.empty();
                        Map<Optional<Application>, Optional<String>> applyResult;
                        applyResult = applicationService.buildApplication(vacancyId.get(), finderId);
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
                        requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, ErrorMessage.ALREADY_WORK + finder.getWorkStatus());
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