package edu.epam.project.model.service.impl;

import edu.epam.project.exception.DaoException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.dao.FinderDao;
import edu.epam.project.model.dao.impl.FinderDaoImpl;
import edu.epam.project.model.entity.Application;
import edu.epam.project.model.entity.Finder;
import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.entity.Vacancy;
import edu.epam.project.model.service.FinderService;
import edu.epam.project.model.service.UserService;
import edu.epam.project.model.util.message.ErrorMessage;
import edu.epam.project.model.validator.UserInputValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FinderServiceImpl implements FinderService {

    private static final FinderServiceImpl instance = new FinderServiceImpl();
    private static final Logger logger = LogManager.getLogger();
    private final FinderDao finderDao = FinderDaoImpl.getInstance();

    private FinderServiceImpl() {
    }

    public static FinderServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean add(Finder entity) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Finder> findAll(int start, int end) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Finder> findById(Integer entityId) throws ServiceException {
        Optional<Finder> foundFinder;
        try {
            foundFinder = finderDao.findById(entityId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return foundFinder;
    }

    @Override
    public void update(Finder entity) throws ServiceException {
        try {
            finderDao.update(entity);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Optional<Finder>, Optional<String>> addFinder(Integer finderId, String requireSalary, String workExperience, String specialtyId) throws ServiceException {
        UserService userService = UserServiceImpl.getInstance();
        Optional<Finder> finder = Optional.empty();
        Optional<String> errorMessage = Optional.empty();
        Optional<Specialty> specialty;
        Map<Optional<Finder>, Optional<String>> addResult = new HashMap<>();
        if (!UserInputValidator.isValidSalary(requireSalary) || !UserInputValidator.isValidWorkExperience(workExperience) || !UserInputValidator.isValidId(specialtyId)) {
            errorMessage = Optional.of(ErrorMessage.ERROR_ADD_FINDER_INFO);
        } else {
            try {
                specialty = userService.findSpecialtyById(Integer.parseInt(specialtyId));
                if (specialty.isPresent()) {
                    finder = Optional.of(new Finder(finderId, BigDecimal.valueOf(Long.parseLong(requireSalary)), Integer.valueOf(workExperience), specialty.get()));
                    finderDao.add(finder.get());
                } else {
                    errorMessage = Optional.of(ErrorMessage.NO_SUCH_SPECIALTY);
                }
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        addResult.put(finder, errorMessage);
        return addResult;
    }

    @Override
    public boolean existsFinder(Integer finderId) throws ServiceException {
        boolean isExist;
        try {
            isExist = finderDao.existsFinder(finderId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return isExist;
    }

    @Override
    public Map<Optional<Application>, Optional<String>> buildApplication(String vacancyId, Integer finderId, boolean isConfirmApplication) throws ServiceException {
        UserService userService = UserServiceImpl.getInstance();
        FinderService finderService = FinderServiceImpl.getInstance();
        Optional<Application> application = Optional.empty();
        Optional<Vacancy> vacancy;
        Optional<Finder> finder;
        Optional<String> errorMessage = Optional.empty();
        Map<Optional<Application>, Optional<String>> buildResult = new HashMap<>();
        if (!UserInputValidator.isValidId(vacancyId)) {
            errorMessage = Optional.of(ErrorMessage.APPLICATION_INCORRECT_PARAMETERS);
        } else {
            try {
                vacancy = userService.findVacancyById(Integer.parseInt(vacancyId));
                finder = finderService.findById(finderId);
                if (vacancy.isPresent() && finder.isPresent()) {
                    application = Optional.of(new Application(0, vacancy.get(), finder.get(), isConfirmApplication));
                    finderDao.buildApplication(application.get());
                } else {
                    errorMessage = Optional.of(ErrorMessage.NO_FINDER_OR_VACANCY);
                }
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException();
            }
        }
        buildResult.put(application, errorMessage);
        return buildResult;
    }

    @Override
    public boolean isFinderApply(Integer finderId) throws ServiceException {
        boolean isExist;
        try {
            isExist = finderDao.isFinderApply(finderId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return isExist;
    }
}