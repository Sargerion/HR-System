package edu.epam.project.controller.command.impl.hr;

import edu.epam.project.controller.command.Command;
import edu.epam.project.controller.command.CommandResult;
import edu.epam.project.controller.command.SessionRequestContext;
import edu.epam.project.exception.CommandException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddVacancyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        return null;
    }
}