package edu.epam.project.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class CommandProvider {

    private static final Logger logger = LogManager.getLogger();

    public static Optional<Command> defineCommand(String commandName) {
        Optional<Command> currentCommand;
        if (commandName == null) {
            return Optional.empty();
        }
        try {
            CommandName command = CommandName.valueOf(commandName.toUpperCase());
            currentCommand = Optional.of(command.getCommand());
        } catch (IllegalArgumentException e) {
            currentCommand = Optional.empty();
            logger.error(e);
        }
        return currentCommand;
    }
}