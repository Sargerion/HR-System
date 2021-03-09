package edu.epam.project.controller.command.impl.hr;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.Vacancy;
import edu.epam.project.model.service.VacancyService;
import edu.epam.project.model.service.impl.VacancyServiceImpl;
import edu.epam.project.model.util.message.FriendlyMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class AddVacancyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final boolean ACTIVE_VACANCY = true;
    private static final String EMPTY_ADDING_VACANCY_PARAMETERS = "Empty adding vacancy parameters";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        VacancyService vacancyService = VacancyServiceImpl.getInstance();
        Optional<String> vacancyName = requestContext.getRequestParameter(RequestParameter.VACANCY_NAME);
        Optional<String> vacancySpecialtyId = requestContext.getRequestParameter(RequestParameter.SPECIALTY);
        Optional<String> vacancySalary = requestContext.getRequestParameter(RequestParameter.VACANCY_MONEY);
        Optional<String> vacancyWorkExperience = requestContext.getRequestParameter(RequestParameter.VACANCY_EXPERIENCE);
        User user = (User) requestContext.getSessionAttribute(SessionAttribute.USER);
        CommandResult commandResult = new CommandResult(PathJsp.CREATE_VACANCY_PAGE, TransitionType.REDIRECT);
        if (vacancyName.isEmpty() || vacancySpecialtyId.isEmpty() || vacancySalary.isEmpty() || vacancyWorkExperience.isEmpty()) {
            requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, EMPTY_ADDING_VACANCY_PARAMETERS);
        } else {
            Optional<Vacancy> vacancy = Optional.empty();
            Optional<String> errorMessage = Optional.empty();
            Map<Optional<Vacancy>, Optional<String>> addResult;
            try {
                addResult = vacancyService.addVacancy(user.getLogin(), vacancyName.get(), vacancySpecialtyId.get(), vacancySalary.get(), vacancyWorkExperience.get(), ACTIVE_VACANCY);
                for (Map.Entry<Optional<Vacancy>, Optional<String>> entry : addResult.entrySet()) {
                    vacancy = entry.getKey();
                    errorMessage = entry.getValue();
                }
                if (errorMessage.isPresent()) {
                    requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, errorMessage.get());
                } else {
                    if (vacancy.isPresent()) {
                        requestContext.setSessionAttribute(SessionAttribute.CONFIRM_MESSAGE, FriendlyMessage.ADD_VACANCY);
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