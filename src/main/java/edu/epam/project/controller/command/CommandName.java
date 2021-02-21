package edu.epam.project.controller.command;

import edu.epam.project.controller.command.impl.admin.*;
import edu.epam.project.controller.command.impl.common.*;
import edu.epam.project.model.entity.UserType;

import java.util.EnumSet;
import java.util.List;

public enum CommandName {

    LOG_IN(new LogInCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN, UserType.COMPANY_HR, UserType.FINDER, UserType.GUEST);
        }
    },
    LOG_OUT(new LogOutCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN, UserType.COMPANY_HR, UserType.FINDER);
        }
    },
    CHANGE_LANGUAGE(new LanguageCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN, UserType.COMPANY_HR, UserType.FINDER, UserType.GUEST);
        }
    },
    REGISTER(new RegisterCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN, UserType.COMPANY_HR, UserType.FINDER, UserType.GUEST);
        }
    },
    ACTIVATE(new ActivateCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN, UserType.COMPANY_HR, UserType.FINDER, UserType.GUEST);
        }
    },
    ACTIVATE_HR(new ActivateHRCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN);
        }
    },
    FORWARD_TO_USER_LIST(new ForwardToUserListCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN);
        }
    },
    USER_LIST(new FindUserListCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN);
        }
    },
    UPLOAD_AVATAR(new UploadAvatarCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN, UserType.COMPANY_HR, UserType.FINDER);
        }
    },
    NOT_ACTIVE_HR_LIST(new FindNotActiveHRListCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN);
        }
    },
    FORWARD_TO_HR_LIST(new ForwardToHRListCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN);
        }
    },
    ADD_COMPANY(new AddCompanyCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN);
        }
    };

    private Command command;
    EnumSet<UserType> allowedUserTypes = EnumSet.noneOf(UserType.class);

    CommandName(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public void setAllowedUserTypes(UserType... userTypes) {
        allowedUserTypes.addAll(List.of(userTypes));
    }

    public boolean isTypeAllowed(UserType userType) {
        return allowedUserTypes.contains(userType);
    }
}