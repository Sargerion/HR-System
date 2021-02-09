package edu.epam.project.validator;

public class UserValidator {

    private static final String LOGIN_REGEX = "^[a-zA-Z0-9_-]{6,15}$";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9_-]{6,15}$";
    private static final String EMAIL_REGEX = "(\\w{3,})@(\\w+\\.)(\\w{2,4})";

    public static boolean isValidLogin(String login) {
        return login.matches(LOGIN_REGEX);
    }

    public static boolean isValidPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }
}
