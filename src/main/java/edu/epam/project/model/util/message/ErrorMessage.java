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

    private ErrorMessage() {

    }
}