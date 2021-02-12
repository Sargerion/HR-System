package edu.epam.project.service.impl;

import edu.epam.project.dao.UserDao;
import edu.epam.project.dao.impl.UsersDaoImpl;
import edu.epam.project.entity.User;
import edu.epam.project.entity.UserStatus;
import edu.epam.project.entity.UserType;
import edu.epam.project.exception.DaoException;
import edu.epam.project.exception.ExceptionMessage;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.service.UserService;
import edu.epam.project.util.Encrypter;
import edu.epam.project.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class UserServiceImpl implements UserService {

    private static final UserServiceImpl instance = new UserServiceImpl();
    private static final Logger logger = LogManager.getLogger();
    private final UserDao userDao = UsersDaoImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean add(User entity) throws ServiceException {
        throw new UnsupportedOperationException("You can't add user by this UserService method");
    }

    @Override
    public List<User> findAll() throws ServiceException {
        return null;
    }

    @Override
    public Optional<User> findById(Integer entityId) throws ServiceException {
        Optional<User> foundUser;
        try {
            foundUser = userDao.findById(entityId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return foundUser;
    }


    @Override
    public void update(User entity) throws ServiceException {
        try {
            userDao.update(entity);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(User entity) throws ServiceException {
        return false;
    }

    @Override
    public boolean deleteById(Integer entityId) throws ServiceException {
        return false;
    }

    @Override
    public Map<Optional<User>, Map<Optional<String>, Optional<String>>> loginUser(String login, String password) throws ServiceException {
        Optional<User> foundUser = Optional.empty();
        Optional<String> errorMessage = Optional.empty();
        Optional<String> correctLogin = Optional.empty();
        Map<Optional<User>, Map<Optional<String>, Optional<String>>> loginResult = new HashMap<>();
        Optional<String> userDbPassword;
        if (!UserValidator.isValidLogin(login) || !UserValidator.isValidPassword(password)) {
            errorMessage = Optional.of(ExceptionMessage.INVALID_LOGIN_OR_PASSWORD);
        }
        try {
            if (userDao.existsLogin(login)) {
                correctLogin = Optional.of(login);
                if (userDao.detectUserStatusByLogin(login) == UserStatus.ACTIVE) {
                    userDbPassword = userDao.findUserPasswordByLogin(login);
                    if (userDbPassword.isPresent()) {
                        if (Encrypter.checkInputPassword(password, userDbPassword.get())) {
                            foundUser = userDao.findUserByLogin(login);
                        } else {
                            errorMessage = Optional.of(ExceptionMessage.INCORRECT_PASSWORD);
                        }
                    }
                } else {
                    errorMessage = Optional.of(ExceptionMessage.NOT_ACTIVE_ACCOUNT);
                }
            } else {
                errorMessage = Optional.of(ExceptionMessage.NO_LOGIN);
            }
            loginResult.put(foundUser, Map.of(errorMessage, correctLogin));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return loginResult;
    }

    @Override
    public Map<Optional<User>, Optional<String>> registerUser(String login, String password, String repeatPassword, String email, boolean isHR) throws ServiceException {
        Optional<User> user = Optional.empty();
        Optional<String> errorMessage = Optional.empty();
        Map<Optional<User>, Optional<String>> registerResult = new HashMap<>();
        if (!UserValidator.isValidLogin(login) || !UserValidator.isValidPassword(password) || !UserValidator.isValidEmail(email)) {
            errorMessage = Optional.of(ExceptionMessage.REGISTER_FAIL_INPUT);
        }
        try {
            if (userDao.existsLogin(login)) {
                errorMessage = Optional.of(ExceptionMessage.LOGIN_ALREADY_EXISTS);
            } else if (!repeatPassword.equals(password)) {
                errorMessage = Optional.of(ExceptionMessage.REGISTER_DIFFERENT_PASSWORDS);
            } else {
                user = Optional.of((isHR) ? new User(0, login, email, UserType.COMPANY_HR, UserStatus.NOT_ACTIVE)
                        : new User(0, login, email, UserType.FINDER, UserStatus.NOT_ACTIVE));
                String encryptedPassword = Encrypter.encryptPassword(password);
                user.get().setConfirmationToken(UUID.randomUUID().toString());
                userDao.addUser(user.get(), encryptedPassword);
            }
            registerResult.put(user, errorMessage);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return registerResult;
    }
}