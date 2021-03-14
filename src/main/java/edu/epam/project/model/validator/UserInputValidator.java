package edu.epam.project.model.validator;

import java.math.BigDecimal;

/**
 * Class UserInputValidator used to validate user input on services.
 * @author Sargerion.
 */
public class UserInputValidator {

    private static final String LOGIN_REGEX = "^[a-zA-Zа-яА-Я0-9_-]{6,15}$";
    private static final String PASSWORD_REGEX = "^[a-zA-Zа-яА-Я0-9_-]{6,15}$";
    private static final String EMAIL_REGEX = "^([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})$";
    private static final String COMPANY_REGEX = "^[a-zA-Zа-яА-Я_-]{1,10}$";
    private static final String OWNER_REGEX = "^[a-zA-Zа-яА-Я_-]{1,25}$";
    private static final String TOWN_REGEX = "^[a-zA-Zа-яА-Я]{1,20}$";
    private static final String DIGIT_REGEX = "\\d+";
    private static final String SALARY_REGEX = "^\\d+(?:[\\.,]\\d+)?$";
    private static final BigDecimal SALARY_LOWER_BOUND = BigDecimal.valueOf(500);
    private static final BigDecimal SALARY_UPPER_BOUND = BigDecimal.valueOf(100000);
    private static final int WORK_EXPERIENCE_LOWER_BOUND = 1;
    private static final int WORK_EXPERIENCE_UPPER_BOUND = 60;
    private static final String VACANCY_NAME_REGEX = "^[a-zA-Zа-яА-Я_-]{1,40}$";
    private static final String SPECIALTY_NAME_REGEX = "^[a-zA-Zа-яА-Я_-]{1,15}$";

    public static boolean isValidLogin(String login) {
        return login.matches(LOGIN_REGEX);
    }

    public static boolean isValidPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static boolean isValidSalary(String salary) {
        boolean result = false;
        if (salary.matches(SALARY_REGEX)) {
            BigDecimal checkSalary = new BigDecimal(salary);
            if (checkSalary.compareTo(SALARY_LOWER_BOUND) >= 0 && checkSalary.compareTo(SALARY_UPPER_BOUND) <= 0) {
                result = true;
            }
        }
        return result;
    }

    public static boolean isValidWorkExperience(String workExperience) {
        boolean result = false;
        if (workExperience.matches(DIGIT_REGEX)) {
            int checkWorkExperience = Integer.parseInt(workExperience);
            if (checkWorkExperience >= WORK_EXPERIENCE_LOWER_BOUND && checkWorkExperience <= WORK_EXPERIENCE_UPPER_BOUND) {
                result = true;
            }
        }
        return result;
    }

    public static boolean isValidId(String id) {
        return id.matches(DIGIT_REGEX);
    }

    public static boolean isValidVacancyName(String vacancyName) {
        return vacancyName.matches(VACANCY_NAME_REGEX);
    }

    public static boolean isValidCompanyName(String name) {
        return name.matches(COMPANY_REGEX);
    }

    public static boolean isValidCompanyOwner(String owner) {
        return owner.matches(OWNER_REGEX);
    }

    public static boolean isValidCompanyTown(String town) {
        return town.matches(TOWN_REGEX);
    }

    public static boolean isValidSpecialtyName(String specialtyName) {
        return specialtyName.matches(SPECIALTY_NAME_REGEX);
    }
}