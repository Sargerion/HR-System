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
import edu.epam.project.util.mail.ConfirmationToken;
import edu.epam.project.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public enum UserServiceImpl implements UserService {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    public static final int EXPIRED_CONFIRMATION_TIME = 15;
    private static final UserDao userDao = new UsersDaoImpl();


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
        Optional<User> foundUser = Optional.empty();
        try {
            foundUser = userDao.findById(entityId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return foundUser;
    }



    @Override
    public boolean update(User entity, Integer entityId) throws ServiceException {
        return false;
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
    public Optional<User> loginUser(String login, String password) throws ServiceException {
        Optional<User> foundUser = Optional.empty();
        Optional<String> userDbPassword;
        if (!UserValidator.isValidLogin(login) || !UserValidator.isValidPassword(password)) {
            throw new ServiceException(ExceptionMessage.INVALID_LOGIN_OR_PASSWORD);
        }
        try {
            if (userDao.existsLogin(login)) {
                if (userDao.detectUserStatusByLogin(login) == UserStatus.ACTIVE) {
                    userDbPassword = userDao.findUserPasswordByLogin(login);
                    if (userDbPassword.isPresent()) {
                        if (Encrypter.checkInputPassword(password, userDbPassword.get())) {
                            foundUser = userDao.findUserByLogin(login);
                        } else {
                            throw new ServiceException(ExceptionMessage.INCORRECT_PASSWORD);
                        }
                    }
                } else {
                    throw new ServiceException(ExceptionMessage.NOT_ACTIVE_ACCOUNT);
                }
            } else {
                throw new ServiceException(ExceptionMessage.NO_LOGIN);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return foundUser;
    }

    @Override
    public Optional<User> registerUser(String login, String password, String repeatPassword, String email, boolean isHR) throws ServiceException {
        Optional<User> user;
        if (!UserValidator.isValidLogin(login) || !UserValidator.isValidPassword(password) || !UserValidator.isValidEmail(email)) {
            throw new ServiceException(ExceptionMessage.REGISTER_FAIL_INPUT);
        }
        if (!repeatPassword.equals(password)) {
            throw new ServiceException(ExceptionMessage.REGISTER_DIFFERENT_PASSWORDS);
        }
        try {
            if (userDao.existsLogin(login)) {
                throw new ServiceException(ExceptionMessage.LOGIN_ALREADY_EXISTS);
            }
            user = Optional.of((isHR) ? new User(0, login, email, UserType.COMPANY_HR, UserStatus.NOT_ACTIVE) :
                    new User(0, login, email, UserType.FINDER, UserStatus.NOT_ACTIVE));
            String encryptedPassword = Encrypter.encryptPassword(password);
            user.get().setConfirmationToken(UUID.randomUUID().toString());
            userDao.addUser(user.get(), encryptedPassword);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public boolean activateUser(User user, String confirmationToken) throws ServiceException {
        boolean activateResult = false;
        User tryActivateUser;
        try {
            Optional<User> foundUser = userDao.findUserByLogin(user.getLogin());
            if (foundUser.isPresent() && confirmationToken.equals(user.getConfirmationToken())) {
                tryActivateUser = foundUser.get();
                tryActivateUser.setStatus(UserStatus.ACTIVE);
                userDao.updateStatus(user);
                activateResult = true;
            }
        } catch (DaoException e) {
            logger.error(ExceptionMessage.DAO_CANT_ACTIVATE);
            throw new ServiceException(e);
        }
        logger.info("User activate");
        return activateResult;
    }

    @Override
    public Optional<String> findUserActivateTokenById(Integer id) throws ServiceException {
        Optional<String> foundToken;
        try {
            foundToken = userDao.findUserActivateTokenById(id);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return foundToken;
    }
}