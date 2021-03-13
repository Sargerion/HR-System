package edu.epam.project.model.service.impl;

import edu.epam.project.controller.command.SessionAttribute;
import edu.epam.project.exception.DaoException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.dao.CompanyDao;
import edu.epam.project.model.dao.impl.CompanyDaoImpl;
import edu.epam.project.model.entity.Company;
import edu.epam.project.model.service.CompanyService;
import edu.epam.project.model.util.message.ErrorMessage;
import edu.epam.project.model.validator.UserInputValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CompanyServiceImpl implements CompanyService {

    private static final CompanyServiceImpl instance = new CompanyServiceImpl();
    private static final Logger logger = LogManager.getLogger();
    private final CompanyDao companyDao = new CompanyDaoImpl();

    private CompanyServiceImpl() {
    }

    public static CompanyServiceImpl getInstance() {
        return instance;
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
                if (companyDao.isExistsCompanyName(companyName) && companyDao.isExistsCompanyHrLogin(companyHrLogin)) {
                    errorMessages.add(ErrorMessage.COMPANY_NAME_DUPLICATE);
                    errorMessages.add(ErrorMessage.COMPANY_HR_LOGIN_DUPLICATE);
                } else if (companyDao.isExistsCompanyName(companyName) && !companyDao.isExistsCompanyHrLogin(companyHrLogin)) {
                    errorMessages.add(ErrorMessage.COMPANY_NAME_DUPLICATE);
                    correctFields.put(SessionAttribute.CORRECT_COMPANY_HR_LOGIN, companyHrLogin);
                } else if (!companyDao.isExistsCompanyName(companyName) && companyDao.isExistsCompanyHrLogin(companyHrLogin)) {
                    errorMessages.add(ErrorMessage.COMPANY_HR_LOGIN_DUPLICATE);
                    correctFields.put(SessionAttribute.CORRECT_COMPANY_NAME, companyName);
                }
                correctFields.put(SessionAttribute.CORRECT_COMPANY_OWNER, companyOwner);
                correctFields.put(SessionAttribute.CORRECT_COMPANY_TOWN, companyAddress);
                if (!companyDao.isExistsCompanyName(companyName) && !companyDao.isExistsCompanyHrLogin(companyHrLogin)) {
                    company = Optional.of(new Company(0, companyName, companyOwner, companyAddress, companyHrLogin));
                    companyDao.add(company.get());
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
    public Map<Optional<Company>, Optional<String>> findCompanyById(String companyId) throws ServiceException {
        Optional<Company> foundCompany = Optional.empty();
        Optional<String> errorMessage = Optional.empty();
        Map<Optional<Company>, Optional<String>> findResult = new HashMap<>();
        if (!UserInputValidator.isValidId(companyId)) {
            errorMessage = Optional.of(ErrorMessage.INCORRECT_FIND_COMPANY_PARAMETERS);
        } else {
            try {
                foundCompany = companyDao.findById(Integer.parseInt(companyId));
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        findResult.put(foundCompany, errorMessage);
        return findResult;
    }

    @Override
    public Optional<Company> findById(Integer companyId) throws ServiceException {
        Optional<Company> foundCompany;
        try {
            foundCompany = companyDao.findById(companyId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return foundCompany;
    }

    @Override
    public Optional<Company> findByHrLogin(String hrLogin) throws ServiceException {
        Optional<Company> foundCompany;
        try {
            foundCompany = companyDao.findCompanyByHrLogin(hrLogin);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return foundCompany;
    }

    @Override
    public Optional<String> findCompanyNameByHrLogin(String hrLogin) throws ServiceException {
        Optional<String> companyName;
        try {
            companyName = companyDao.findCompanyNameByHrLogin(hrLogin);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return companyName;
    }

    @Override
    public boolean isCompanyHr(String hrLogin) throws ServiceException {
        boolean isCompanyHr;
        try {
            isCompanyHr = companyDao.isExistsCompanyHrLogin(hrLogin);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return isCompanyHr;
    }

    @Override
    public void update(Company company) throws ServiceException {
        try {
            companyDao.update(company);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Company> findAll(int start, int end) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Integer entityId) throws ServiceException {
        throw new UnsupportedOperationException();
    }
}