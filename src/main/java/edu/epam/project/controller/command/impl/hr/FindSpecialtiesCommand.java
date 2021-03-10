package edu.epam.project.controller.command.impl.hr;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.service.SpecialtyService;
import edu.epam.project.model.service.impl.SpecialtyServiceImpl;

import edu.epam.project.model.util.message.FriendlyMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FindSpecialtiesCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        SpecialtyService specialtyService = SpecialtyServiceImpl.getInstance();
        CommandResult commandResult = new CommandResult(PathJsp.CREATE_VACANCY_PAGE, TransitionType.REDIRECT);
        List<Specialty> currentSpecialties = (List<Specialty>) requestContext.getSessionAttribute(SessionAttribute.SPECIALTY_LIST);
        List<Specialty> specialties;
        try {
            specialties = specialtyService.findAllSpecialties();
            if (currentSpecialties.equals(specialties)) {
                requestContext.setSessionAttribute(SessionAttribute.CONFIRM_MESSAGE, FriendlyMessage.NO_NEW_SPECIALTIES);
            } else {
                requestContext.setSessionAttribute(SessionAttribute.SPECIALTY_LIST, specialties);
                requestContext.setSessionAttribute(SessionAttribute.CONFIRM_MESSAGE, FriendlyMessage.EXISTS_NEW_SPECIALTIES);
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
        return commandResult;
    }
}