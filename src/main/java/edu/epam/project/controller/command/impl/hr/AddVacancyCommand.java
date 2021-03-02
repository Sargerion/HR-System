package edu.epam.project.controller.command.impl.hr;

import edu.epam.project.controller.command.Command;
import edu.epam.project.controller.command.CommandResult;
import edu.epam.project.controller.command.SessionRequestContext;
import edu.epam.project.exception.CommandException;

import edu.epam.project.model.service.HrService;
import edu.epam.project.model.service.impl.HrServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddVacancyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_ADDING_VACANCY_PARAMETERS = "Empty adding vacancy parameters";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        HrService hrService = HrServiceImpl.getInstance();

        return null;
    }
}