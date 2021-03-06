package edu.epam.project.controller.command.impl.admin;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.service.AdminService;
import edu.epam.project.model.service.impl.AdminServiceImpl;
import edu.epam.project.model.util.message.FriendlyMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class AddSpecialtyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_ADDING_SPECIALTY_PARAMETERS = "Empty adding specialty parameters";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        AdminService adminService = AdminServiceImpl.getInstance();
        Optional<String> specialtyName = requestContext.getRequestParameter(RequestParameter.SPECIALTY_NAME);
        CommandResult commandResult = new CommandResult(PathJsp.ADD_SPECIALTY_PAGE, TransitionType.REDIRECT);
        if (specialtyName.isEmpty()) {
            requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, EMPTY_ADDING_SPECIALTY_PARAMETERS);
        } else {
            Optional<Specialty> specialty = Optional.empty();
            Optional<String> errorMessage = Optional.empty();
            Map<Optional<Specialty>, Optional<String>> addResult;
            try {
                addResult = adminService.addSpecialty(specialtyName.get());
                for (Map.Entry<Optional<Specialty>, Optional<String>> entry : addResult.entrySet()) {
                    specialty = entry.getKey();
                    errorMessage = entry.getValue();
                }
                if (errorMessage.isPresent()) {
                    requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, errorMessage.get());
                } else {
                    if (specialty.isPresent()) {
                        requestContext.setSessionAttribute(SessionAttribute.CONFIRM_MESSAGE, FriendlyMessage.ADD_SPECIALTY);
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