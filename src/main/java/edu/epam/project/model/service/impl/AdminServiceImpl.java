package edu.epam.project.model.service.impl;

import edu.epam.project.controller.command.SessionAttribute;
import edu.epam.project.model.dao.AdminDao;
import edu.epam.project.model.dao.impl.AdminDaoImpl;
import edu.epam.project.model.entity.*;
import edu.epam.project.exception.DaoException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.service.AdminService;

import edu.epam.project.model.util.message.ErrorMessage;
import edu.epam.project.model.validator.UserInputValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class AdminServiceImpl implements AdminService {

    private static final AdminServiceImpl instance = new AdminServiceImpl();
    private static final Logger logger = LogManager.getLogger();
    private final AdminDao adminDao = AdminDaoImpl.getInstance();

    private AdminServiceImpl() {
    }

    public static AdminServiceImpl getInstance() {
        return instance;
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
        throw new UnsupportedOperationException();
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
    public Map<Optional<Company>, Map<List<String>, Map<String, String>>> addCompany(String companyName, String companyOwner, String companyAddress, String companyHrLogin) throws ServiceException {
        Optional<Company> company = Optional.empty();
        List<String> errorMessages = new ArrayList<>();
        Map<String, String> correctFields = new HashMap<>();
        Map<Optional<Company>, Map<List<String>, Map<String, String>>> addResult = new HashMap<>();
        if (!UserInputValidator.isValidCompanyName(companyName) || !UserInputValidator.isValidCompanyOwner(companyOwner)
                || !UserInputValidator.isValidCompanyTown(companyAddress) || !UserInputValidator.isValidLogin(companyHrLogin)) {
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
                if (!adminDao.existsCompanyName(companyName) && !adminDao.existsCompanyHrLogin(companyHrLogin)) {
                    company = Optional.of(new Company(0, companyName, companyOwner, companyAddress, companyHrLogin));
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
    public Map<Optional<Specialty>, Optional<String>> addSpecialty(String specialtyName) throws ServiceException {
        Optional<Specialty> specialty = Optional.empty();
        Optional<String> errorMessage = Optional.empty();
        Map<Optional<Specialty>, Optional<String>> addResult = new HashMap<>();
        if (!UserInputValidator.isValidSpecialtyName(specialtyName)) {
            errorMessage = Optional.of(ErrorMessage.INCORRECT_ADD_SPECIALTY_PARAMETERS);
        } else {
            try {
                if (adminDao.existsSpecialtyName(specialtyName)) {
                    errorMessage = Optional.of(ErrorMessage.SPECIALTY_NAME_DUPLICATE);
                } else {
                    specialty = Optional.of(new Specialty(0, specialtyName.toLowerCase()));
                    adminDao.addSpecialty(specialty.get());
                }
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        addResult.put(specialty, errorMessage);
        return addResult;
    }

    @Override
    public boolean isCompanyHr(String companyHrLogin) throws ServiceException {
        boolean isExists;
        try {
            isExists = adminDao.existsCompanyHrLogin(companyHrLogin);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return isExists;
    }

    @Override
    public Optional<User> findById(Integer entityId) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(User entity) throws ServiceException {
        throw new UnsupportedOperationException();
    }
}
