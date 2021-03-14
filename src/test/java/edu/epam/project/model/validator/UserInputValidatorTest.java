package edu.epam.project.model.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UserInputValidatorTest {
    @Test
    public void isValidLoginTest() {
        String login = "JustTest";
        boolean actualResult = UserInputValidator.isValidLogin(login);
        Assert.assertTrue(actualResult);
    }

    @Test
    public void isNotValidLoginTest() {
        String login = "J1";
        boolean actualResult = UserInputValidator.isValidLogin(login);
        Assert.assertFalse(actualResult);
    }

    @Test
    public void isValidSalaryTest() {
        String salary = "4942.00";
        boolean actualResult = UserInputValidator.isValidSalary(salary);
        Assert.assertTrue(actualResult);
    }

    @Test
    public void isNotValidSalaryTest() {
        String salary = "42.00";
        boolean actualResult = UserInputValidator.isValidSalary(salary);
        Assert.assertFalse(actualResult);
    }

    @Test
    public void isValidEmailTest() {
        String email = "epam@mail.com";
        boolean actualResult = UserInputValidator.isValidEmail(email);
        Assert.assertTrue(actualResult);
    }

    @Test
    public void isNotValidEmailTest() {
        String email = "@mail.com";
        boolean actualResult = UserInputValidator.isValidEmail(email);
        Assert.assertFalse(actualResult);
    }
}