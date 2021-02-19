package edu.epam.project.controller.command;

import edu.epam.project.controller.command.impl.admin.ActivateHRCommand;
import edu.epam.project.controller.command.impl.admin.FindUserListCommand;
import edu.epam.project.controller.command.impl.admin.ForwardToUserListCommand;
import edu.epam.project.controller.command.impl.common.*;

public enum CommandName {

    LOG_IN(new LogInCommand()),
    LOG_OUT(new LogOutCommand()),
    CHANGE_LANGUAGE(new LanguageCommand()),
    REGISTER(new RegisterCommand()),
    ACTIVATE(new ActivateCommand()),
    ACTIVATE_HR(new ActivateHRCommand()),
    FORWARD_TO_USER_LIST(new ForwardToUserListCommand()),
    USER_LIST(new FindUserListCommand()),
    UPLOAD_AVATAR(new UploadAvatarCommand());

    private Command command;

    CommandName(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
