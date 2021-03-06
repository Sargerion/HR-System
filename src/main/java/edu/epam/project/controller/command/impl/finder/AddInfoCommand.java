package edu.epam.project.controller.command.impl.finder;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Finder;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.service.FinderService;
import edu.epam.project.model.service.impl.FinderServiceImpl;
import edu.epam.project.model.util.message.ErrorMessage;
import edu.epam.project.model.util.message.FriendlyMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class AddInfoCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_ADDING_INFO_PARAMETERS = "Empty adding finder info parameters";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        FinderService finderService = FinderServiceImpl.getInstance();
        Optional<String> finderRequireSalary = requestContext.getRequestParameter(RequestParameter.FINDER_REQUIRE_SALARY);
        Optional<String> finderWorkExperience = requestContext.getRequestParameter(RequestParameter.FINDER_WORK_EXPERIENCE);
        Optional<String> specialtyId = requestContext.getRequestParameter(RequestParameter.SPECIALTY);
        CommandResult commandResult = new CommandResult(PathJsp.ADD_FINDER_INFO_PAGE, TransitionType.REDIRECT);
        if (finderRequireSalary.isEmpty() || finderWorkExperience.isEmpty() || specialtyId.isEmpty()) {
            requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, EMPTY_ADDING_INFO_PARAMETERS);
        } else {
            Optional<Finder> optionalFinder = Optional.empty();
            Optional<String> errorMessage = Optional.empty();
            Map<Optional<Finder>, Optional<String>> addResult;
            try {
                User user = (User) requestContext.getSessionAttribute(SessionAttribute.USER);
                if (!finderService.existsFinder(user.getEntityId())) {
                    addResult = finderService.addFinder(user.getEntityId(), finderRequireSalary.get(), finderWorkExperience.get(), specialtyId.get());
                    for (Map.Entry<Optional<Finder>, Optional<String>> entry : addResult.entrySet()) {
                        optionalFinder = entry.getKey();
                        errorMessage = entry.getValue();
                    }
                    if (errorMessage.isPresent()) {
                        requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, errorMessage.get());
                    } else {
                        if (optionalFinder.isPresent()) {
                            requestContext.setSessionAttribute(SessionAttribute.CONFIRM_MESSAGE, FriendlyMessage.ADD_FINDER_INFO);
                        }
                    }
                } else {
                    requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, ErrorMessage.ALREADY_ADD_FINDER_INFO);
                }
            } catch (ServiceException e) {
                logger.error(e);
                throw new CommandException(e);
            }
        }
        return commandResult;
    }
}