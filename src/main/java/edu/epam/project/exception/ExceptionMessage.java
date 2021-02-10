package edu.epam.project.exception;

public class ExceptionMessage {

    public static final String INVALID_LOGIN_OR_PASSWORD = "Invalid login or password";
    public static final String INCORRECT_PASSWORD = "Incorrect password";
    public static final String NOT_ACTIVE_ACCOUNT = "Account is not active";
    public static final String NO_LOGIN = "No such login";
    public static final String REGISTER_FAIL_INPUT = "Invalid login or password or email, check template";
    public static final String REGISTER_DIFFERENT_PASSWORDS = "Repeat password is different";
    public static final String LOGIN_ALREADY_EXISTS = "Login has already existed";
    public static final String DAO_CANT_ACTIVATE = "Can't activate user";

    private ExceptionMessage() {

    }
}