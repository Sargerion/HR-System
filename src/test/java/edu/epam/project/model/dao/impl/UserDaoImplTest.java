package edu.epam.project.model.dao.impl;

import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.model.dao.UserDao;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.UserStatus;
import edu.epam.project.model.entity.UserType;
import edu.epam.project.model.pool.ConnectionPool;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;

public class UserDaoImplTest {

    private UserDao userDao;

    @BeforeClass
    public void initialize() {
        userDao = new UserDaoImpl();
    }

    @Test
    public void addTest() throws DaoException {
        User addingUser = new User(0, "Test", "test@mail.com", UserType.FINDER, UserStatus.NOT_ACTIVE, "TestToken");
        String testPassword = "Not hashed";
        userDao.addUser(addingUser, testPassword);
        Optional<User> actualResult = userDao.findById(addingUser.getEntityId());
        Assert.assertTrue(actualResult.isPresent());
        userDao.deleteById(actualResult.get().getEntityId());
    }

    @Test
    public void countUsersTest() throws DaoException {
        User tempUser = new User(0, "Test", "test@mail.com", UserType.FINDER, UserStatus.NOT_ACTIVE, "TestToken");
        String testPassword = "Not hashed";
        int currentUsersCount = userDao.countUsers();
        userDao.addUser(tempUser, testPassword);
        int newUsersCount = userDao.countUsers();
        userDao.deleteById(tempUser.getEntityId());
        Assert.assertEquals(currentUsersCount + 1, newUsersCount);
    }

    @Test
    public void detectUserStatusByLoginTest() throws DaoException {
        User tempUser = new User(0, "Test", "test@mail.com", UserType.FINDER, UserStatus.NOT_ACTIVE, "TestToken");
        String testPassword = "Not hashed";
        userDao.addUser(tempUser, testPassword);
        UserStatus expectedResult = UserStatus.NOT_ACTIVE;
        UserStatus actualResult = userDao.detectUserStatusByLogin(tempUser.getLogin());
        userDao.deleteById(tempUser.getEntityId());
        Assert.assertEquals(actualResult, expectedResult);
    }

    @AfterClass
    public void clear() {
        userDao = null;
        try {
            ConnectionPool.getInstance().destroyPool();
        } catch (ConnectionException e) {
            Assert.fail(e.getMessage());
        }
    }
}