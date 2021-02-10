package edu.epam.project.command;

import edu.epam.project.command.impl.*;
import edu.epam.project.command.impl.admin.ActivateHRCommand;

public enum CommandName {

    LOG_IN(new LogInCommand()),
    LOG_OUT(new LogOutCommand()),
    CHANGE_LANGUAGE(new LanguageCommand()),
    REGISTER(new RegisterCommand()),
    ACTIVATE(new ActivateCommand()),
    ACTIVATE_HR(new ActivateHRCommand());

    private Command command;

    CommandName(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
