package edu.epam.project.model.service.impl;

import edu.epam.project.controller.command.RequestAttribute;
import edu.epam.project.controller.command.RequestParameter;
import edu.epam.project.model.dao.UserDao;
import edu.epam.project.model.dao.impl.UsersDaoImpl;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.UserStatus;
import edu.epam.project.model.entity.UserType;
import edu.epam.project.exception.DaoException;
import edu.epam.project.model.util.message.ErrorMessage;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.service.UserService;
import edu.epam.project.model.util.Encrypter;
import edu.epam.project.model.validator.UserInputValidator;
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
    public List<User> findAll(int start, int end) throws ServiceException {
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
    public void updateAvatar(User user) throws ServiceException {
        try {
            userDao.updateAvatar(user);
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
        if (!UserInputValidator.isValidLogin(login) || !UserInputValidator.isValidPassword(password)) {
            errorMessage = Optional.of(ErrorMessage.INVALID_LOGIN_OR_PASSWORD);
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
                            errorMessage = Optional.of(ErrorMessage.INCORRECT_PASSWORD);
                        }
                    }
                } else {
                    errorMessage = Optional.of(ErrorMessage.NOT_ACTIVE_ACCOUNT);
                }
            } else {
                errorMessage = Optional.of(ErrorMessage.NO_LOGIN);
            }
            loginResult.put(foundUser, Map.of(errorMessage, correctLogin));
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return loginResult;
    }

    @Override
    public Map<Optional<User>, Map<List<String>, Map<String, String>>> registerUser(String login, String password, String repeatPassword, String email, boolean isHR) throws ServiceException {
        Optional<User> user = Optional.empty();
        List<String> errorMessages = new ArrayList<>();
        Map<String, String> correctFields = new HashMap<>();
        Map<Optional<User>, Map<List<String>, Map<String, String>>> registerResult = new HashMap<>();
        if (!UserInputValidator.isValidLogin(login) || !UserInputValidator.isValidPassword(password) || !UserInputValidator.isValidEmail(email)) {
            errorMessages.add(ErrorMessage.REGISTER_FAIL_INPUT);
        }
        try {
            if (userDao.existsLogin(login) && !repeatPassword.equals(password)) {
                errorMessages.add(ErrorMessage.LOGIN_ALREADY_EXISTS);
                errorMessages.add(ErrorMessage.REGISTER_DIFFERENT_PASSWORDS);
            } else if (!repeatPassword.equals(password) && !userDao.existsLogin(login)) {
                correctFields.put(RequestAttribute.CORRECT_LOGIN, login);
                errorMessages.add(ErrorMessage.REGISTER_DIFFERENT_PASSWORDS);
            } else if (userDao.existsLogin(login) && repeatPassword.equals(password)) {
                errorMessages.add(ErrorMessage.LOGIN_ALREADY_EXISTS);
                correctFields.put(RequestAttribute.CORRECT_PASSWORD, password);
                correctFields.put(RequestAttribute.CORRECT_REPEAT_PASSWORD, repeatPassword);
            }
            correctFields.put(RequestAttribute.CORRECT_EMAIL, email);
            if (isHR) {
                correctFields.put(RequestAttribute.HR_CHECK, RequestParameter.HR_CHECK_BOX);
            } else {
                correctFields.put(RequestAttribute.HR_CHECK, "");
            }
            if (!userDao.existsLogin(login) && repeatPassword.equals(password)) {
                user = Optional.of((isHR) ? new User(0, login, email, UserType.COMPANY_HR, UserStatus.NOT_ACTIVE)
                        : new User(0, login, email, UserType.FINDER, UserStatus.NOT_ACTIVE));
                String encryptedPassword = Encrypter.encryptPassword(password);
                user.get().setConfirmationToken(UUID.randomUUID().toString());
                userDao.addUser(user.get(), encryptedPassword);
            }
            registerResult.put(user, Map.of(errorMessages, correctFields));
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return registerResult;
    }

    @Override
    public Optional<String> findUserAvatar(User user) throws ServiceException {
        Optional<String> userAvatar;
        try {
            userAvatar = userDao.findUserAvatar(user);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return userAvatar;
    }
}