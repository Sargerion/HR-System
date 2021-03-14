package edu.epam.project.model.service.impl;

import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.dao.UserDao;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.UserStatus;
import edu.epam.project.model.entity.UserType;
import edu.epam.project.model.pool.ConnectionPool;
import edu.epam.project.model.service.UserService;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

public class UserServiceImplTest {

    @Mock
    private UserDao userDao;
    private UserService userService;

    @BeforeClass
    public void initialize() {
        MockitoAnnotations.initMocks(this);
        userService = UserServiceImpl.getInstance();
    }

    @Test
    public void findByIdTest() {
        int userId = 1;
        Optional<User> expectedUser = Optional.of(new User(userId, "sergio_1", "sergey.galyan16@gmail.com", UserType.ADMIN, UserStatus.ACTIVE, ""));
        Optional<User> actualUser = Optional.empty();
        try {
            given(userDao.findById(userId)).willReturn(expectedUser);
            actualUser = userService.findById(userId);
        } catch (DaoException | ServiceException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(actualUser, expectedUser);
    }

    @Test
    public void detectUserStatusByLoginTest() {
        String userLogin = "sergio_1";
        UserStatus expectedStatus = UserStatus.ACTIVE;
        UserStatus actualStatus = null;
        try {
            given(userDao.detectUserStatusByLogin(userLogin)).willReturn(expectedStatus);
            actualStatus = userService.detectUserStatusByLogin(userLogin);
        } catch (DaoException | ServiceException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(actualStatus, expectedStatus);
    }

    @Test
    public void findAllTest() {
        int start = 0;
        int end = 2;
        List<User> expectedResult = List.of(
                new User(1, "sergio_1", "sergey.galyan16@gmail.com", UserType.ADMIN, UserStatus.ACTIVE, ""),
                new User(108, "JustFinder", "jwd.epam@gmail.com", UserType.FINDER, UserStatus.ACTIVE, "confirm")
        );
        List<User> actualResult = new ArrayList<>();
        try {
            given(userDao.findAll(start, end)).willReturn(expectedResult);
            actualResult = userService.findAll(start, end);
        } catch (DaoException | ServiceException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(actualResult, expectedResult);
    }

    @AfterClass
    public void clear() {
        userService = null;
        try {
            ConnectionPool.getInstance().destroyPool();
        } catch (ConnectionException e) {
            Assert.fail(e.getMessage());
        }
    }
}