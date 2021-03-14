package edu.epam.project.controller.command.impl.common;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Vacancy;
import edu.epam.project.model.service.VacancyService;
import edu.epam.project.model.service.impl.VacancyServiceImpl;
import edu.epam.project.model.util.message.FriendlyMessage;
import edu.epam.project.tag.ViewAllVacanciesTag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * The implementation of Command interface for finding vacancy list by common authorized user types.
 * @author Sargerion.
 */
public class FindVacancyListCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        Integer currentTablePage = (Integer) requestContext.getSessionAttribute(SessionAttribute.VACANCY_LIST_CURRENT_PAGE);
        Optional<String> nextPage = requestContext.getRequestParameter(RequestParameter.NEW_PAGE);
        if (nextPage.isEmpty() && currentTablePage == null) {
            currentTablePage = 1;
        } else if (nextPage.isPresent()) {
            currentTablePage = Integer.parseInt(nextPage.get());
        }
        requestContext.setSessionAttribute(SessionAttribute.VACANCY_LIST_CURRENT_PAGE, currentTablePage);
        int start = (currentTablePage - 1) * ViewAllVacanciesTag.LIST_LINES_COUNT;
        int end = ViewAllVacanciesTag.LIST_LINES_COUNT + start;
        VacancyService vacancyService = VacancyServiceImpl.getInstance();
        try {
            List<Vacancy> vacancies = vacancyService.findAll(start, end);
            requestContext.setSessionAttribute(SessionAttribute.VACANCY_PAGINATE_LIST, vacancies);
            int vacanciesCount = vacancyService.countVacancies();
            requestContext.setSessionAttribute(SessionAttribute.VACANCIES_COUNT, vacanciesCount);
            if (vacancies.size() == 0) {
                requestContext.setRequestAttribute(RequestAttribute.CONFIRM_MESSAGE, FriendlyMessage.EMPTY_VACANCY_LIST);
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
        return new CommandResult(PathJsp.VACANCIES_VIEW_PAGE, TransitionType.FORWARD);
    }
}