package edu.epam.project.command;

import edu.epam.project.command.impl.LanguageCommand;
import edu.epam.project.command.impl.LogInCommand;
import edu.epam.project.command.impl.RegisterCommand;

public enum CommandName {

    LOG_IN(new LogInCommand()),
    CHANGE_LANGUAGE(new LanguageCommand()),
    REGISTER(new RegisterCommand());

    private Command command;

    CommandName(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
