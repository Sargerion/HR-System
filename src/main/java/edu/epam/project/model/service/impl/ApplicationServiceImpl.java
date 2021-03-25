package edu.epam.project.model.service.impl;

import edu.epam.project.exception.DaoException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.dao.ApplicationDao;
import edu.epam.project.model.dao.impl.ApplicationDaoImpl;
import edu.epam.project.model.entity.Application;
import edu.epam.project.model.entity.Finder;
import edu.epam.project.model.entity.Vacancy;
import edu.epam.project.model.service.ApplicationService;
import edu.epam.project.model.service.FinderService;
import edu.epam.project.model.service.VacancyService;
import edu.epam.project.model.util.message.ErrorMessage;
import edu.epam.project.model.validator.UserInputValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * The ApplicationService implementation, with Singleton design pattern.
 * @author Sargerion.
 */
public class ApplicationServiceImpl implements ApplicationService {

    private static final Logger logger = LogManager.getLogger();
    private static final ApplicationServiceImpl instance = new ApplicationServiceImpl();
    private final ApplicationDao applicationDao = new ApplicationDaoImpl();

    private ApplicationServiceImpl() {
    }

    public static ApplicationServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Map<Optional<Application>, Optional<String>> buildApplication(String vacancyId, Integer finderId) throws ServiceException {
        VacancyService vacancyService = VacancyServiceImpl.getInstance();
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
                vacancy = vacancyService.findById(Integer.parseInt(vacancyId));
                finder = finderService.findById(finderId);
                if (vacancy.isPresent() && finder.isPresent()) {
                    if (vacancy.get().getSpecialty().equals(finder.get().getSpecialty())) {
                        application = Optional.of(new Application(0, vacancy.get(), finder.get()));
                        applicationDao.add(application.get());
                    } else {
                        errorMessage = Optional.of(ErrorMessage.DIFFERENT_SPECIALTIES);
                    }
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
    public Map<Integer, Optional<String>> confirmApplication(String applicationId, String noHireString) throws ServiceException {
        FinderService finderService = FinderServiceImpl.getInstance();
        VacancyService vacancyService = VacancyServiceImpl.getInstance();
        Optional<String> errorMessage = Optional.empty();
        int finderId = 0;
        Map<Integer, Optional<String>> result = new HashMap<>();
        if (!UserInputValidator.isValidId(applicationId)) {
            errorMessage = Optional.of(ErrorMessage.CONFIRM_APPLICATION_INCORRECT_PARAMETERS);
        } else {
            try {
                if (applicationDao.isExistsApplicationId(Integer.parseInt(applicationId))) {
                    Map<Integer, Integer> vacancyFinderIdPair = applicationDao.findPairVacancyAndFinderId(Integer.parseInt(applicationId));
                    int vacancyId = 0;
                    for (Map.Entry<Integer, Integer> entry : vacancyFinderIdPair.entrySet()) {
                        vacancyId = entry.getKey();
                        finderId = entry.getValue();
                    }
                    Finder finder = finderService.findById(finderId).get();
                    if (finder.getWorkStatus().equals(noHireString)) {
                        applicationDao.deleteById(Integer.parseInt(applicationId));
                        vacancyService.deleteById(vacancyId);
                    } else {
                        errorMessage = Optional.of(ErrorMessage.ALREADY_WORK_FINDER + finder.getWorkStatus());
                        applicationDao.deleteById(Integer.parseInt(applicationId));
                    }
                } else {
                    errorMessage = Optional.of(ErrorMessage.NO_APPLICATION);
                }
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        result.put(finderId, errorMessage);
        return result;
    }

    @Override
    public Map<Integer, Optional<String>> rejectApplication(String applicationId, String noHireString) throws ServiceException {
        FinderService finderService = FinderServiceImpl.getInstance();
        VacancyService vacancyService = VacancyServiceImpl.getInstance();
        Optional<String> errorMessage = Optional.empty();
        int finderId = 0;
        Map<Integer, Optional<String>> result = new HashMap<>();
        if (!UserInputValidator.isValidId(applicationId)) {
            errorMessage = Optional.of(ErrorMessage.CONFIRM_APPLICATION_INCORRECT_PARAMETERS);
        } else {
            try {
                if (applicationDao.isExistsApplicationId(Integer.parseInt(applicationId))) {
                    Map<Integer, Integer> vacancyFinderIdPair = applicationDao.findPairVacancyAndFinderId(Integer.parseInt(applicationId));
                    int vacancyId = 0;
                    for (Map.Entry<Integer, Integer> entry : vacancyFinderIdPair.entrySet()) {
                        vacancyId = entry.getKey();
                        finderId = entry.getValue();
                    }
                    Finder finder = finderService.findById(finderId).get();
                    if (finder.getWorkStatus().equals(noHireString)) {
                        applicationDao.deleteById(Integer.parseInt(applicationId));
                        vacancyService.updateVacancyStatus(true, vacancyId);
                    } else {
                        applicationDao.deleteById(Integer.parseInt(applicationId));
                        errorMessage = Optional.of(ErrorMessage.ALREADY_WORK_FINDER + finder.getWorkStatus());
                    }
                } else {
                    errorMessage = Optional.of(ErrorMessage.NO_APPLICATION);
                }
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        result.put(finderId, errorMessage);
        return result;
    }

    @Override
    public List<Application> findAll(int start, int end) throws ServiceException {
        List<Application> applications;
        try {
            applications = new ArrayList<>(applicationDao.findAll(start, end));
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return applications;
    }

    @Override
    public int countApplications() throws ServiceException {
        int applicationsCount;
        try {
            applicationsCount = applicationDao.countApplications();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return applicationsCount;
    }

    @Override
    public Optional<Application> findById(Integer applicationId) throws ServiceException {
        Optional<Application> application;
        try {
            application = applicationDao.findById(applicationId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return application;
    }

    @Override
    public boolean isFinderApply(Integer vacancyId, Integer finderId) throws ServiceException {
        boolean isExists;
        try {
            isExists = applicationDao.isFinderApply(vacancyId, finderId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return isExists;
    }

    @Override
    public void update(Application entity) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Integer entityId) throws ServiceException {
        throw new UnsupportedOperationException();
    }
}