package edu.epam.project.controller.command.impl.common;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Vacancy;
import edu.epam.project.model.service.UserService;
import edu.epam.project.model.service.impl.UserServiceImpl;
import edu.epam.project.model.util.message.FriendlyMessage;
import edu.epam.project.tag.ViewAllVacanciesTag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class FindVacancyListCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        Integer currentTablePage = (Integer) requestContext.getSessionAttribute(SessionAttribute.VACANCY_LIST_CURRENT_PAGE);
        Optional<String> currentPage = requestContext.getRequestParameter(RequestParameter.NEW_PAGE);
        if (currentPage.isEmpty() && currentTablePage == null) {
            currentTablePage = 1;
        } else if (currentPage.isPresent()) {
            currentTablePage = Integer.parseInt(currentPage.get());
        }
        requestContext.setSessionAttribute(SessionAttribute.VACANCY_LIST_CURRENT_PAGE, currentTablePage);
        int start = (currentTablePage - 1) * ViewAllVacanciesTag.LIST_LINES_COUNT;
        int end = ViewAllVacanciesTag.LIST_LINES_COUNT + start;
        UserService userService = UserServiceImpl.getInstance();
        try {
            List<Vacancy> vacancies = userService.findAllVacancies(start, end);
            requestContext.setSessionAttribute(SessionAttribute.VACANCY_PAGINATE_LIST, vacancies);
            int vacanciesCount = userService.countVacancies();
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