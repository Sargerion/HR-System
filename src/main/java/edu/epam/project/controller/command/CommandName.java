package edu.epam.project.controller.command;

import edu.epam.project.controller.command.impl.admin.*;
import edu.epam.project.controller.command.impl.common.*;
import edu.epam.project.controller.command.impl.finder.*;
import edu.epam.project.controller.command.impl.hr.*;
import edu.epam.project.model.entity.UserType;

import java.util.EnumSet;
import java.util.List;

/**
 * The enum Command name, implementations of Command interface.
 * @author Sargerion.
 */
public enum CommandName {

    /**
     * Performance of LogInCommand implementation.
     */
    LOG_IN(new LogInCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN, UserType.COMPANY_HR, UserType.FINDER, UserType.GUEST);
        }
    },

    /**
     * The Performance of LogOutCommand implementation.
     */
    LOG_OUT(new LogOutCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN, UserType.COMPANY_HR, UserType.FINDER);
        }
    },

    /**
     * The Performance of LanguageCommand implementation.
     */
    CHANGE_LANGUAGE(new LanguageCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN, UserType.COMPANY_HR, UserType.FINDER, UserType.GUEST);
        }
    },

    /**
     * The Performance of RegisterCommand implementation.
     */
    REGISTER(new RegisterCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN, UserType.COMPANY_HR, UserType.FINDER, UserType.GUEST);
        }
    },

    /**
     * The Performance of ActivateCommand implementation.
     */
    ACTIVATE(new ActivateCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN, UserType.COMPANY_HR, UserType.FINDER, UserType.GUEST);
        }
    },

    /**
     * The Performance of FindUserListCommand implementation.
     */
    USER_LIST(new FindUserListCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN);
        }
    },

    /**
     * The Performance of UploadAvatarCommand implementation.
     */
    UPLOAD_AVATAR(new UploadAvatarCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN, UserType.COMPANY_HR, UserType.FINDER);
        }
    },

    /**
     * The Performance of FindVacancyListCommand implementation.
     */
    FIND_VACANCY_LIST(new FindVacancyListCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN, UserType.COMPANY_HR, UserType.FINDER);
        }
    },

    /**
     * The Performance of AddCompanyCommand implementation.
     */
    ADD_COMPANY(new AddCompanyCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN);
        }
    },

    /**
     * The Performance of AddInfoCommand implementation.
     */
    ADD_FINDER_INFO(new AddInfoCommand()) {
        {
            setAllowedUserTypes(UserType.FINDER);
        }
    },

    /**
     * The Performance of AddVacancyCommand implementation.
     */
    ADD_VACANCY(new AddVacancyCommand()) {
        {
            setAllowedUserTypes(UserType.COMPANY_HR);
        }
    },

    /**
     * The Performance of AddSpecialtyCommand implementation.
     */
    ADD_SPECIALTY(new AddSpecialtyCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN);
        }
    },

    /**
     * The Performance of ConfirmApplicationCommand implementation.
     */
    CONFIRM_APPLICATION(new ConfirmApplicationCommand()) {
        {
            setAllowedUserTypes(UserType.COMPANY_HR);
        }
    },

    /**
     * The Performance of RejectApplicationCommand implementation.
     */
    REJECT_APPLICATION(new RejectApplicationCommand()) {
        {
            setAllowedUserTypes(UserType.COMPANY_HR);
        }
    },

    /**
     * The Performance of FindApplicationListCommand implementation.
     */
    FIND_APPLICATION_LIST(new FindApplicationListCommand()) {
        {
            setAllowedUserTypes(UserType.COMPANY_HR);
        }
    },

    /**
     * The Performance of ApplyVacancyCommand implementation.
     */
    APPLY_VACANCY(new ApplyVacancyCommand()) {
        {
            setAllowedUserTypes(UserType.FINDER);
        }
    },

    /**
     * The Performance of BlockUserCommand implementation.
     */
    BLOCK_USER(new BlockUserCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN);
        }
    },

    /**
     * The Performance of UnblockUserCommand implementation.
     */
    UNBLOCK_USER(new UnblockUserCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN);
        }
    },

    /**
     * The Performance of FindSpecialtiesCommand implementation.
     */
    FIND_SPECIALTIES(new FindSpecialtiesCommand()) {
        {
            setAllowedUserTypes(UserType.COMPANY_HR);
        }
    },

    /**
     * The Performance of EditFinderInfoCommand implementation.
     */
    EDIT_FINDER_INFO(new EditFinderInfoCommand()) {
        {
            setAllowedUserTypes(UserType.FINDER);
        }
    },

    /**
     * The Performance of ForwardToEditInfoCommand implementation.
     */
    FORWARD_TO_EDIT_INFO(new ForwardToEditInfoCommand()) {
        {
            setAllowedUserTypes(UserType.FINDER);
        }
    },

    /**
     * The Performance of FindCompanyInfoCommand implementation.
     */
    FIND_COMPANY_INFO(new FindCompanyInfoCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN, UserType.COMPANY_HR, UserType.FINDER);
        }
    },

    /**
     * The Performance of ViewFinderProfileCommand implementation.
     */
    VIEW_FINDER_PROFILE(new ViewFinderProfileCommand()) {
        {
            setAllowedUserTypes(UserType.COMPANY_HR);
        }
    },

    /**
     * The Performance of ForwardToChangeAvatarCommand implementation.
     */
    FORWARD_TO_CHANGE_AVATAR(new ForwardToChangeAvatarCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN, UserType.COMPANY_HR, UserType.FINDER);
        }
    },

    /**
     * The Performance of ForwardToAddCompanyCommand implementation.
     */
    FORWARD_TO_ADD_COMPANY(new ForwardToAddCompanyCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN);
        }
    },

    /**
     * The Performance of ForwardToAddSpecialtyCommand implementation.
     */
    FORWARD_TO_ADD_SPECIALTY(new ForwardToAddSpecialtyCommand()) {
        {
            setAllowedUserTypes(UserType.ADMIN);
        }
    },

    /**
     * The Performance of ForwardToAddInfoCommand implementation.
     */
    FORWARD_TO_ADD_FINDER_INFO(new ForwardToAddInfoCommand()) {
        {
            setAllowedUserTypes(UserType.FINDER);
        }
    },

    /**
     * The Performance of ForwardToAddVacancyCommand implementation.
     */
    FORWARD_TO_ADD_VACANCY(new ForwardToAddVacancyCommand()) {
        {
            setAllowedUserTypes(UserType.COMPANY_HR);
        }
    };

    private Command command;

    /**
     * Allowed user types for command.
     */
    private EnumSet<UserType> allowedUserTypes = EnumSet.noneOf(UserType.class);

    CommandName(Command command) {
        this.command = command;
    }

    /**
     * Gets command.
     *
     * @return implementation of Command interface.
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Sets allowed user types.
     *
     * @param userTypes the user types
     */
    public void setAllowedUserTypes(UserType... userTypes) {
        allowedUserTypes.addAll(List.of(userTypes));
    }

    /**
     * Is user type allowed to given command.
     *
     * @param userType the user type
     * @return the boolean, to access the command
     */
    public boolean isTypeAllowed(UserType userType) {
        return allowedUserTypes.contains(userType);
    }
}
