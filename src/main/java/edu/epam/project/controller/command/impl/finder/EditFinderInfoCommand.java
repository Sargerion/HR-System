package edu.epam.project.controller.command.impl.finder;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Finder;
import edu.epam.project.model.service.FinderService;
import edu.epam.project.model.service.impl.FinderServiceImpl;
import edu.epam.project.model.util.message.FriendlyMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

/**
 * The implementation of Command interface for editing info by finder user type.
 * @author Sargerion.
 */
public class EditFinderInfoCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_EDIT_INFO_PARAMETERS = "Empty edit finder info parameters";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        FinderService finderService = FinderServiceImpl.getInstance();
        Optional<String> finderRequireSalary = requestContext.getRequestParameter(RequestParameter.FINDER_REQUIRE_SALARY);
        Optional<String> finderWorkExperience = requestContext.getRequestParameter(RequestParameter.FINDER_WORK_EXPERIENCE);
        Optional<String> specialtyId = requestContext.getRequestParameter(RequestParameter.SPECIALTY);
        CommandResult commandResult = new CommandResult(PathJsp.EDIT_FINDER_INFO_PAGE, TransitionType.REDIRECT);
        if (finderRequireSalary.isEmpty() || finderWorkExperience.isEmpty() || specialtyId.isEmpty()) {
            requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, EMPTY_EDIT_INFO_PARAMETERS);
        } else {
            Optional<Finder> newFinder = Optional.empty();
            Optional<String> errorMessage = Optional.empty();
            Map<Optional<Finder>, Optional<String>> editResult;
            Finder currentFinder = (Finder) requestContext.getSessionAttribute(SessionAttribute.FINDER);
            try {
                editResult = finderService.editInfo(currentFinder, finderRequireSalary.get(), finderWorkExperience.get(), specialtyId.get());
                for (Map.Entry<Optional<Finder>, Optional<String>> entry : editResult.entrySet()) {
                    newFinder = entry.getKey();
                    errorMessage = entry.getValue();
                }
                if (errorMessage.isPresent()) {
                    requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, errorMessage.get());
                } else {
                    if (newFinder.isPresent()) {
                        requestContext.setSessionAttribute(SessionAttribute.CONFIRM_MESSAGE, FriendlyMessage.EDIT_FINDER_INFO);
                        requestContext.setSessionAttribute(SessionAttribute.FINDER, newFinder.get());
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