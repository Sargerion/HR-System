package edu.epam.project.model.validator;

public class UserInputValidator {

    private static final String LOGIN_REGEX = "^[a-zA-Zа-яА-Я0-9_-]{6,15}$";
    private static final String PASSWORD_REGEX = "^[a-zA-Zа-яА-Я0-9_-]{6,15}$";
    private static final String EMAIL_REGEX = "^([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})$";
    private static final String COMPANY_REGEX = "^[a-zA-Zа-яА-Я]{10}$";
    private static final String OWNER_REGEX = "^[a-zA-Zа-яА-Я]{25}$";
    private static final String TOWN_REGEX = "^[a-zA-Zа-яА-Я]{20}$";

    public static boolean isValidLogin(String login) {
        return login.matches(LOGIN_REGEX);
    }

    public static boolean isValidPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static boolean isValidCompanyName(String companyName) {
        return companyName.matches(COMPANY_REGEX);
    }

    public static boolean isValidOwner(String owner) {
        return owner.matches(OWNER_REGEX);
    }

    public static boolean isValidTown(String town) {
        return town.matches(TOWN_REGEX);
    }
}
