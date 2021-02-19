package edu.epam.project.controller.command;

import edu.epam.project.exception.CommandException;

@FunctionalInterface
public interface Command {
    CommandResult execute(SessionRequestContext requestContext) throws CommandException;
}