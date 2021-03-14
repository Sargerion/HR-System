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
    public void findByIdTest() throws DaoException, ServiceException {
        int userId = 1;
        Optional<User> expectedUser = Optional.of(new User(userId, "sergio_1", "sergey.galyan16@gmail.com", UserType.ADMIN, UserStatus.ACTIVE, ""));
        given(userDao.findById(userId)).willReturn(expectedUser);
        Optional<User> actualUser = userService.findById(userId);
        Assert.assertEquals(actualUser, expectedUser);
    }

    @Test
    public void detectUserStatusByLoginTest() throws DaoException, ServiceException {
        String userLogin = "sergio_1";
        UserStatus expectedStatus = UserStatus.ACTIVE;
        given(userDao.detectUserStatusByLogin(userLogin)).willReturn(expectedStatus);
        UserStatus actualStatus = userService.detectUserStatusByLogin(userLogin);
        Assert.assertEquals(actualStatus, expectedStatus);
    }

    @Test
    public void findAllTest() throws DaoException, ServiceException {
        int start = 0;
        int end = 2;
        List<User> expectedResult = List.of(
                new User(1, "sergio_1", "sergey.galyan16@gmail.com", UserType.ADMIN, UserStatus.ACTIVE, ""),
                new User(108, "JustFinder", "jwd.epam@gmail.com", UserType.FINDER, UserStatus.ACTIVE, "confirm")
        );
        given(userDao.findAll(start, end)).willReturn(expectedResult);
        List<User> actualResult = userService.findAll(start, end);
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