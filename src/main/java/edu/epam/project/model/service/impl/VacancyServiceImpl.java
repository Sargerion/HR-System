package edu.epam.project.model.service.impl;

import edu.epam.project.exception.DaoException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.dao.VacancyDao;
import edu.epam.project.model.dao.impl.VacancyDaoImpl;
import edu.epam.project.model.entity.Company;
import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.entity.Vacancy;
import edu.epam.project.model.service.CompanyService;
import edu.epam.project.model.service.SpecialtyService;
import edu.epam.project.model.service.VacancyService;
import edu.epam.project.model.util.message.ErrorMessage;
import edu.epam.project.model.validator.UserInputValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

public class VacancyServiceImpl implements VacancyService {

    private static final VacancyServiceImpl instance = new VacancyServiceImpl();
    private static final Logger logger = LogManager.getLogger();
    private final VacancyDao vacancyDao = new VacancyDaoImpl();

    private VacancyServiceImpl() {
    }

    public static VacancyServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Map<Optional<Vacancy>, Optional<String>> addVacancy(String hrLogin, String vacancyName, String specialtyId, String requireSalary, String workExperience, boolean isVacancyActive) throws ServiceException {
        SpecialtyService specialtyService = SpecialtyServiceImpl.getInstance();
        CompanyService companyService = CompanyServiceImpl.getInstance();
        Optional<Vacancy> vacancy = Optional.empty();
        Optional<Specialty> vacancySpecialty;
        Optional<Company> vacancyCompany;
        Optional<String> errorMessage = Optional.empty();
        Map<Optional<Vacancy>, Optional<String>> addResult = new HashMap<>();
        if (!UserInputValidator.isValidLogin(hrLogin) || !UserInputValidator.isValidVacancyName(vacancyName) || !UserInputValidator.isValidId(specialtyId)
                || !UserInputValidator.isValidSalary(requireSalary) || !UserInputValidator.isValidWorkExperience(workExperience)) {
            errorMessage = Optional.of(ErrorMessage.ADD_VACANCY_INCORRECT_PARAMETERS);
        } else {
            try {
                vacancySpecialty = specialtyService.findById(Integer.parseInt(specialtyId));
                vacancyCompany = companyService.findByHrLogin(hrLogin);
                if (vacancySpecialty.isPresent() && vacancyCompany.isPresent()) {
                    vacancy = Optional.of(new Vacancy(0, vacancyName, vacancySpecialty.get(), new BigDecimal(requireSalary), Integer.valueOf(workExperience), vacancyCompany.get(), isVacancyActive));
                    vacancyDao.add(vacancy.get());
                } else {
                    errorMessage = Optional.of(ErrorMessage.NO_SUCH_VACANCY_OR_COMPANY);
                }
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        addResult.put(vacancy, errorMessage);
        return addResult;
    }

    @Override
    public void update(Vacancy entity) throws ServiceException {

    }

    @Override
    public List<Vacancy> findAll(int start, int end) throws ServiceException {
        List<Vacancy> vacancies;
        try {
            vacancies = new ArrayList<>(vacancyDao.findAll(start, end));
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return vacancies;
    }

    @Override
    public int countVacancies() throws ServiceException {
        int vacanciesCount;
        try {
            vacanciesCount = vacancyDao.countVacancies();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return vacanciesCount;
    }

    @Override
    public Optional<Vacancy> findById(Integer vacancyId) throws ServiceException {
        Optional<Vacancy> vacancy;
        try {
            vacancy = vacancyDao.findById(vacancyId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return vacancy;
    }

    @Override
    public void updateVacancyStatus(boolean isActive, Integer vacancyId) throws ServiceException {
        try {
            vacancyDao.updateVacancyStatus(isActive, vacancyId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteById(Integer vacancyId) throws ServiceException {
        try {
            vacancyDao.deleteById(vacancyId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}