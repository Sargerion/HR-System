package edu.epam.project.service.impl;

import edu.epam.project.dao.UserDao;
import edu.epam.project.dao.impl.UsersDaoImpl;
import edu.epam.project.entity.User;
import edu.epam.project.entity.UserStatus;
import edu.epam.project.entity.UserType;
import edu.epam.project.exception.DaoException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.service.UserService;
import edu.epam.project.util.Encrypter;
import edu.epam.project.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public enum UserServiceImpl implements UserService {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
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
    public Optional<User> findById(Long entityId) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public boolean update(User entity, Long entityId) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(User entity) throws ServiceException {
        return false;
    }

    @Override
    public boolean deleteById(Long entityId) throws ServiceException {
        return false;
    }

    @Override
    public Optional<User> loginUser(String login, String password) throws ServiceException {
        Optional<User> foundUser = Optional.empty();
        Optional<String> userDbPassword;
        if (!UserValidator.isValidLogin(login) || !UserValidator.isValidPassword(password)) {
            throw new ServiceException("Invalid login or password");
        }
        try {
            if (userDao.existsLogin(login)) {
                userDbPassword = userDao.findUserPasswordByLogin(login);
                if (userDbPassword.isPresent()) {
                    if (Encrypter.checkInputPassword(password, userDbPassword.get())) {
                        foundUser = userDao.findUserByLogin(login);
                    }
                }
            } else {
                throw new ServiceException("No such login");
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
            throw new ServiceException("Invalid login or password or email, check template");
        }
        try {
            if (userDao.existsLogin(login)) {
                throw new ServiceException("Login has already existed");
            }
            user = Optional.of((isHR) ? new User(0, login, email, UserType.COMPANY_HR, UserStatus.NOT_ACTIVE) :
                    new User(0, login, email, UserType.FINDER, UserStatus.NOT_ACTIVE));
            String encryptedPassword = Encrypter.encryptPassword(password);
            userDao.addUser(user.get(), encryptedPassword);
            //todo: sendActivation
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return user;
    }
}