package edu.epam.project.command;

import edu.epam.project.exception.CommandException;

@FunctionalInterface
public interface Command {
    CommandResult execute(SessionRequestContext requestContext) throws CommandException;
}