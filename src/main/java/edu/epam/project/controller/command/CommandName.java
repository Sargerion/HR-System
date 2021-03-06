package edu.epam.project.controller.command;

import edu.epam.project.controller.command.impl.admin.*;
import edu.epam.project.controller.command.impl.common.*;
import edu.epam.project.controller.command.impl.finder.AddInfoCommand;
import edu.epam.project.controller.command.impl.finder.ApplyVacancyCommand;
import edu.epam.project.controller.command.impl.hr.*;
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
    FIND_VACANCY_LIST(new FindVacancyListCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN, UserType.COMPANY_HR, UserType.FINDER);
        }
    },
    FORWARD_TO_VACANCY_LIST(new ForwardToVacancyListCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN, UserType.COMPANY_HR, UserType.FINDER);
        }
    },
    ADD_COMPANY(new AddCompanyCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN);
        }
    },
    ADD_FINDER_INFO(new AddInfoCommand()) {
        {
            setAllowedUserTypes(UserType.FINDER);
        }
    },
    ADD_VACANCY(new AddVacancyCommand()) {
        {
            setAllowedUserTypes(UserType.COMPANY_HR);
        }
    },
    ADD_SPECIALTY(new AddSpecialtyCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN);
        }
    },
    CONFIRM_APPLICATION(new ConfirmApplicationCommand()) {
        {
            setAllowedUserTypes(UserType.COMPANY_HR);
        }
    },
    REJECT_APPLICATION(new RejectApplicationCommand()) {
        {
            setAllowedUserTypes(UserType.COMPANY_HR);
        }
    },
    FIND_APPLICATION_LIST(new FindApplicationListCommand()) {
        {
            setAllowedUserTypes(UserType.COMPANY_HR);
        }
    },
    FORWARD_TO_APPLICATION_LIST(new ForwardToApplicationListCommand()) {
        {
            setAllowedUserTypes(UserType.COMPANY_HR);
        }
    },
    APPLY_VACANCY(new ApplyVacancyCommand()) {
        {
            setAllowedUserTypes(UserType.FINDER);
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