package edu.epam.project.model.service.impl;

import edu.epam.project.exception.DaoException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.dao.HrDao;
import edu.epam.project.model.dao.impl.HrDaoImpl;
import edu.epam.project.model.entity.Hr;
import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.entity.Vacancy;
import edu.epam.project.model.service.HrService;

import edu.epam.project.model.service.UserService;
import edu.epam.project.model.util.message.ErrorMessage;
import edu.epam.project.model.validator.UserInputValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

public class HrServiceImpl implements HrService {

    private static final HrServiceImpl instance = new HrServiceImpl();
    private static final Logger logger = LogManager.getLogger();
    private final HrDao hrDao = HrDaoImpl.getInstance();

    private HrServiceImpl() {
    }

    public static HrServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean add(Hr entity) throws ServiceException {
        return false;
    }

    @Override
    public List<Hr> findAll(int start, int end) throws ServiceException {
        return null;
    }

    @Override
    public Optional<Hr> findById(Integer entityId) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public void update(Hr entity) throws ServiceException {

    }

    @Override
    public boolean delete(Hr entity) throws ServiceException {
        return false;
    }

    @Override
    public boolean deleteById(Integer entityId) throws ServiceException {
        return false;
    }

    @Override
    public Map<Optional<Vacancy>, Optional<String>> addVacancy(String vacancyName, String specialtyId, String requireSalary, String workExperience) throws ServiceException {
        UserService userService = UserServiceImpl.getInstance();
        Optional<Vacancy> vacancy = Optional.empty();
        Specialty vacancySpecialty;
        Optional<String> errorMessage = Optional.empty();
        Map<Optional<Vacancy>, Optional<String>> addResult = new HashMap<>();
        if (!UserInputValidator.isValidVacancyName(vacancyName) || !UserInputValidator.isValidSalary(requireSalary) || !UserInputValidator.isValidWorkExperience(workExperience)) {
            errorMessage = Optional.of(ErrorMessage.ADD_VACANCY_INCORRECT_PARAMETERS);
        } else {
            try {
                vacancySpecialty = userService.findSpecialtyById(Integer.parseInt(specialtyId));
                vacancy = Optional.of(new Vacancy(0, vacancyName, vacancySpecialty, BigDecimal.valueOf(Long.parseLong(requireSalary)), Integer.valueOf(workExperience)));
                hrDao.addVacancy(vacancy.get());
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        addResult.put(vacancy, errorMessage);
        return addResult;
    }
}