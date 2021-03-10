package edu.epam.project.model.util.message;

public class ErrorMessage {

    public static final String INVALID_LOGIN_OR_PASSWORD = "Invalid login or password";
    public static final String INCORRECT_PASSWORD = "Incorrect password";
    public static final String NOT_ACTIVE_ACCOUNT = "Account is not active";
    public static final String NO_LOGIN = "No such login";
    public static final String REGISTER_FAIL_INPUT = "Invalid login or password or email, check template";
    public static final String REGISTER_DIFFERENT_PASSWORDS = "Repeat password is different";
    public static final String LOGIN_ALREADY_EXISTS = "Login has already existed";
    public static final String ERROR_SECOND_TRANSIT_BY_LINK = "You've already active, you can just login";
    public static final String ERROR_WITH_UPLOAD = "Please select file, you choose nothing";
    public static final String ERROR_ACCESS = "Access denied";
    public static final String ERROR_COMMAND = "No such command";
    public static final String ERROR_ADD_FINDER_INFO = "Incorrect adding finder info parameters";
    public static final String ADD_VACANCY_INCORRECT_PARAMETERS = "Incorrect adding vacancy parameters";
    public static final String ALREADY_ADD_FINDER_INFO = "Finder info have already existed, please go to edit info page, if you need";
    public static final String INCORRECT_ADD_COMPANY_PARAMETERS = "Incorrect adding company parameters";
    public static final String COMPANY_NAME_DUPLICATE = "Company with this name has already existed";
    public static final String COMPANY_HR_LOGIN_DUPLICATE = "Hr with this login has already worked in company";
    public static final String INCORRECT_ADD_SPECIALTY_PARAMETERS = "Incorrect adding specialty parameters";
    public static final String SPECIALTY_NAME_DUPLICATE = "Specialty with this name has already existed";
    public static final String NOT_REGISTER_LIKE_HR = "Btw you can't register like company hr";
    public static final String APPLICATION_INCORRECT_PARAMETERS = "Incorrect add application parameters";
    public static final String FINDER_WITHOUT_INFO = "If you want to use this service, please fill info about you, on add info page";
    public static final String NO_FINDER_OR_VACANCY = "No such finder or vacancy";
    public static final String NO_SUCH_SPECIALTY = "No such specialty";
    public static final String NO_SUCH_VACANCY_OR_COMPANY = "No such vacancy or company";
    public static final String DIFFERENT_SPECIALTIES = "You can't apply because you have another specialization";
    public static final String CONFIRM_APPLICATION_INCORRECT_PARAMETERS = "Incorrect confirm application parameters";
    public static final String NO_APPLICATION = "Try to delete not existing application";
    public static final String ALREADY_WORK = "You can't apply because you've already worked in ";
    public static final String ALREADY_WORK_FINDER = "You can't apply this finder because he's already worked in ";
    public static final String INCORRECT_BLOCK_USER_PARAMETERS = "Incorrect block user parameters";
    public static final String TRY_BLOCK_NOT_EXISTING_USER = "Trying to block not existing user";
    public static final String INCORRECT_UNBLOCK_USER_PARAMETERS = "Incorrect unblock user parameters";
    public static final String TRY_UNBLOCK_NOT_EXISTING_USER = "Trying to unblock not existing user";
    public static final String ERROR_EDIT_FINDER_INFO = "Incorrect edit finder info parameters";
    public static final String IDENTICAL_EDIT_FINDER_INFO = "The information is identical, no editing has been performed";

    private ErrorMessage() {

    }
}