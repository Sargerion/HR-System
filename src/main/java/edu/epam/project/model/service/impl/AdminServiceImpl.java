package edu.epam.project.model.service.impl;

import edu.epam.project.controller.command.SessionAttribute;
import edu.epam.project.model.dao.AdminDao;
import edu.epam.project.model.dao.UserDao;
import edu.epam.project.model.dao.impl.AdminDaoImpl;
import edu.epam.project.model.dao.impl.UserDaoImpl;
import edu.epam.project.model.entity.Company;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.UserStatus;
import edu.epam.project.exception.DaoException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Vacancy;
import edu.epam.project.model.service.AdminService;

import edu.epam.project.model.service.UserService;
import edu.epam.project.model.util.message.ErrorMessage;
import edu.epam.project.model.validator.UserInputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class AdminServiceImpl implements AdminService {

    private static final AdminServiceImpl instance = new AdminServiceImpl();
    private static final Logger logger = LogManager.getLogger();
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final AdminDao adminDao = AdminDaoImpl.getInstance();

    private AdminServiceImpl() {
    }

    public static AdminServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean activateHR(User user) throws ServiceException {
        boolean activateResult = false;
        User tryActivateUser;
        try {
            Optional<User> foundUser = userDao.findUserByLogin(user.getLogin());
            if (foundUser.isPresent()) {
                tryActivateUser = foundUser.get();
                tryActivateUser.setStatus(UserStatus.ACTIVE);
                userDao.updateStatus(tryActivateUser);
                activateResult = true;
            }
        } catch (DaoException e) {
            logger.error("Can't activate HR");
            throw new ServiceException(e);
        }
        logger.info("HR activate");
        return activateResult;
    }

    @Override
    public List<User> findNotActiveHRList(int start, int end) throws ServiceException {
        List<User> notActiveHRList;
        try {
            notActiveHRList = new ArrayList<>(adminDao.findNotActiveHRs(start, end));
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return notActiveHRList;
    }

    @Override
    public int countNotActiveHRs() throws ServiceException {
        int notActiveHRsCount;
        try {
            notActiveHRsCount = adminDao.countNotActiveHRs();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return notActiveHRsCount;
    }

    @Override
    public boolean add(User entity) throws ServiceException {
        return false;
    }

    @Override
    public List<User> findAll(int start, int end) throws ServiceException {
        List<User> allUsers;
        try {
            allUsers = new ArrayList<>(adminDao.findAll(start, end));
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
            usersCount = adminDao.countUsers();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return usersCount;
    }

    @Override
    public Map<Optional<Company>, Map<List<String>, Map<String, String>>> addCompany(String companyName, String companyOwner, String companyAddress, String vacancyId, String companyHrLogin) throws ServiceException {
        UserService userService = UserServiceImpl.getInstance();
        Vacancy vacancy;
        Optional<Company> company = Optional.empty();
        List<String> errorMessages = new ArrayList<>();
        Map<String, String> correctFields = new HashMap<>();
        Map<Optional<Company>, Map<List<String>, Map<String, String>>> addResult = new HashMap<>();
        if (!UserInputValidator.isValidCompanyName(companyName) || !UserInputValidator.isValidCompanyOwner(companyOwner) || !UserInputValidator.isValidCompanyTown(companyAddress) ||
                !UserInputValidator.isValidId(vacancyId) || !UserInputValidator.isValidLogin(companyHrLogin)) {
            errorMessages.add(ErrorMessage.INCORRECT_ADD_COMPANY_PARAMETERS);
        } else {
            try {
                if (adminDao.existsCompanyName(companyName) && adminDao.existsCompanyHrLogin(companyHrLogin)) {
                    errorMessages.add(ErrorMessage.COMPANY_NAME_DUPLICATE);
                    errorMessages.add(ErrorMessage.COMPANY_HR_LOGIN_DUPLICATE);
                } else if (adminDao.existsCompanyName(companyName) && !adminDao.existsCompanyHrLogin(companyHrLogin)) {
                    errorMessages.add(ErrorMessage.COMPANY_NAME_DUPLICATE);
                    correctFields.put(SessionAttribute.CORRECT_COMPANY_HR_LOGIN, companyHrLogin);
                } else if (!adminDao.existsCompanyName(companyName) && adminDao.existsCompanyHrLogin(companyHrLogin)) {
                    errorMessages.add(ErrorMessage.COMPANY_HR_LOGIN_DUPLICATE);
                    correctFields.put(SessionAttribute.CORRECT_COMPANY_NAME, companyName);
                }
                correctFields.put(SessionAttribute.CORRECT_COMPANY_OWNER, companyOwner);
                correctFields.put(SessionAttribute.CORRECT_COMPANY_TOWN, companyAddress);
                correctFields.put(SessionAttribute.CORRECT_VACANCY_ID, vacancyId);
                vacancy = userService.findVacancyById(Integer.parseInt(vacancyId));
                correctFields.put(SessionAttribute.CORRECT_VACANCY_NAME, vacancy.getName());
                if (!adminDao.existsCompanyName(companyName) && !adminDao.existsCompanyHrLogin(companyHrLogin)) {
                    company = Optional.of(new Company(0, companyName, companyOwner, companyAddress, vacancy, companyHrLogin));
                    adminDao.addCompany(company.get());
                }
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        addResult.put(company, Map.of(errorMessages, correctFields));
        return addResult;
    }

    @Override
    public Optional<User> findById(Integer entityId) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public void update(User entity) throws ServiceException {

    }

    @Override
    public boolean delete(User entity) throws ServiceException {
        return false;
    }

    @Override
    public boolean deleteById(Integer entityId) throws ServiceException {
        return false;
    }
}
