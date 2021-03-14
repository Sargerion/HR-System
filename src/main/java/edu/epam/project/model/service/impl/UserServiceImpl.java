package edu.epam.project.model.service.impl;

import edu.epam.project.controller.command.RequestParameter;
import edu.epam.project.controller.command.SessionAttribute;
import edu.epam.project.model.dao.UserDao;
import edu.epam.project.model.dao.impl.UserDaoImpl;
import edu.epam.project.model.entity.*;
import edu.epam.project.exception.DaoException;
import edu.epam.project.model.service.CompanyService;
import edu.epam.project.model.util.message.ErrorMessage;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.service.UserService;
import edu.epam.project.model.util.Encryptor;
import edu.epam.project.model.validator.UserInputValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class UserServiceImpl implements UserService {

    private static final UserServiceImpl instance = new UserServiceImpl();
    private static final Logger logger = LogManager.getLogger();
    private final UserDao userDao = new UserDaoImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
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
        } else {
            try {
                if (userDao.existsLogin(login)) {
                    correctLogin = Optional.of(login);
                    if (userDao.detectUserStatusByLogin(login) == UserStatus.ACTIVE) {
                        userDbPassword = userDao.findUserPasswordByLogin(login);
                        if (userDbPassword.isPresent()) {
                            if (Encryptor.checkInputPassword(password, userDbPassword.get())) {
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
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        loginResult.put(foundUser, Map.of(errorMessage, correctLogin));
        return loginResult;
    }

    @Override
    public Map<Optional<User>, Map<List<String>, Map<String, String>>> registerUser(String login, String password, String repeatPassword, String email, boolean isHR) throws ServiceException {
        CompanyService companyService = CompanyServiceImpl.getInstance();
        Optional<User> user = Optional.empty();
        List<String> errorMessages = new ArrayList<>();
        Map<String, String> correctFields = new HashMap<>();
        Map<Optional<User>, Map<List<String>, Map<String, String>>> registerResult = new HashMap<>();
        if (!UserInputValidator.isValidLogin(login) || !UserInputValidator.isValidPassword(password) || !UserInputValidator.isValidEmail(email)) {
            errorMessages.add(ErrorMessage.REGISTER_FAIL_INPUT);
        } else {
            try {
                if (userDao.existsLogin(login) && !repeatPassword.equals(password)) {
                    errorMessages.add(ErrorMessage.LOGIN_ALREADY_EXISTS);
                    errorMessages.add(ErrorMessage.REGISTER_DIFFERENT_PASSWORDS);
                } else if (!repeatPassword.equals(password) && !userDao.existsLogin(login)) {
                    correctFields.put(SessionAttribute.CORRECT_LOGIN, login);
                    errorMessages.add(ErrorMessage.REGISTER_DIFFERENT_PASSWORDS);
                } else if (userDao.existsLogin(login) && repeatPassword.equals(password)) {
                    errorMessages.add(ErrorMessage.LOGIN_ALREADY_EXISTS);
                    correctFields.put(SessionAttribute.CORRECT_PASSWORD, password);
                    correctFields.put(SessionAttribute.CORRECT_REPEAT_PASSWORD, repeatPassword);
                }
                correctFields.put(SessionAttribute.CORRECT_EMAIL, email);
                if (isHR && companyService.isCompanyHr(login)) {
                    correctFields.put(SessionAttribute.HR_CHECK, RequestParameter.HR_CHECK_BOX);
                } else if (isHR && !companyService.isCompanyHr(login)) {
                    correctFields.put(SessionAttribute.HR_CHECK, "");
                    errorMessages.add(ErrorMessage.NOT_REGISTER_LIKE_HR);
                } else {
                    correctFields.put(SessionAttribute.HR_CHECK, "");
                }
                if (!userDao.existsLogin(login) && repeatPassword.equals(password)) {
                    String encryptedPassword = Encryptor.encryptPassword(password);
                    if (!isHR) {
                        user = Optional.of(new User(0, login, email, UserType.FINDER, UserStatus.NOT_ACTIVE));
                        user.get().setConfirmationToken(UUID.randomUUID().toString());
                        userDao.addUser(user.get(), encryptedPassword);
                    } else if (companyService.isCompanyHr(login)) {
                        user = Optional.of(new User(0, login, email, UserType.COMPANY_HR, UserStatus.NOT_ACTIVE));
                        user.get().setConfirmationToken(UUID.randomUUID().toString());
                        userDao.addUser(user.get(), encryptedPassword);
                    }
                }
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        registerResult.put(user, Map.of(errorMessages, correctFields));
        return registerResult;
    }

    @Override
    public Optional<String> blockUser(String userId) throws ServiceException {
        Optional<String> errorMessage = Optional.empty();
        if (!UserInputValidator.isValidId(userId)) {
            errorMessage = Optional.of(ErrorMessage.INCORRECT_BLOCK_USER_PARAMETERS);
        } else {
            try {
                if (userDao.existId(Integer.parseInt(userId))) {
                    userDao.updateStatus(UserStatus.BLOCKED, Integer.parseInt(userId));
                } else {
                    errorMessage = Optional.of(ErrorMessage.TRY_BLOCK_NOT_EXISTING_USER);
                }
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        return errorMessage;
    }

    @Override
    public Optional<String> unblockUser(String userId) throws ServiceException {
        Optional<String> errorMessage = Optional.empty();
        if (!UserInputValidator.isValidId(userId)) {
            errorMessage = Optional.of(ErrorMessage.INCORRECT_UNBLOCK_USER_PARAMETERS);
        } else {
            try {
                if (userDao.existId(Integer.parseInt(userId))) {
                    userDao.updateStatus(UserStatus.ACTIVE, Integer.parseInt(userId));
                } else {
                    errorMessage = Optional.of(ErrorMessage.TRY_UNBLOCK_NOT_EXISTING_USER);
                }
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        return errorMessage;
    }

    @Override
    public List<User> findAll(int start, int end) throws ServiceException {
        List<User> allUsers;
        try {
            allUsers = new ArrayList<>(userDao.findAll(start, end));
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return allUsers;
    }

    @Override
    public int countUsers() throws ServiceException {
        int usersCount;
        try {
            usersCount = userDao.countUsers();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return usersCount;
    }

    @Override
    public Optional<User> findById(Integer userId) throws ServiceException {
        Optional<User> foundUser;
        try {
            foundUser = userDao.findById(userId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return foundUser;
    }


    @Override
    public void update(User user) throws ServiceException {
        try {
            userDao.update(user);
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

    @Override
    public UserStatus detectUserStatusByLogin(String login) throws ServiceException {
        UserStatus userStatus;
        try {
            userStatus = userDao.detectUserStatusByLogin(login);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return userStatus;
    }

    @Override
    public void deleteById(Integer entityId) throws ServiceException {
        try {
            userDao.deleteById(entityId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}