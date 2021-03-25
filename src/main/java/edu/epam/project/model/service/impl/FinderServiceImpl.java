package edu.epam.project.model.service.impl;

import edu.epam.project.exception.DaoException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.dao.FinderDao;
import edu.epam.project.model.dao.impl.FinderDaoImpl;
import edu.epam.project.model.entity.Finder;
import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.service.FinderService;
import edu.epam.project.model.service.SpecialtyService;
import edu.epam.project.model.util.message.ErrorMessage;
import edu.epam.project.model.validator.UserInputValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The FinderService implementation, with Singleton design pattern.
 * @author Sargerion.
 */
public class FinderServiceImpl implements FinderService {

    private static final Logger logger = LogManager.getLogger();
    private static final FinderServiceImpl instance = new FinderServiceImpl();
    private final FinderDao finderDao = new FinderDaoImpl();

    private FinderServiceImpl() {
    }

    public static FinderServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Map<Optional<Finder>, Optional<String>> addFinder(Integer finderId, String requireSalary, String workExperience, String specialtyId) throws ServiceException {
        SpecialtyService specialtyService = SpecialtyServiceImpl.getInstance();
        Optional<Finder> finder = Optional.empty();
        Optional<String> errorMessage = Optional.empty();
        Optional<Specialty> specialty;
        Map<Optional<Finder>, Optional<String>> addResult = new HashMap<>();
        if (!UserInputValidator.isValidSalary(requireSalary) || !UserInputValidator.isValidWorkExperience(workExperience) || !UserInputValidator.isValidId(specialtyId)) {
            errorMessage = Optional.of(ErrorMessage.ERROR_ADD_FINDER_INFO);
        } else {
            try {
                specialty = specialtyService.findById(Integer.parseInt(specialtyId));
                if (specialty.isPresent()) {
                    finder = Optional.of(new Finder(finderId, new BigDecimal(requireSalary),
                            Integer.valueOf(workExperience), specialty.get(), Finder.getNotHireStatus()));
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
    public Map<Optional<Finder>, Optional<String>> editInfo(Finder currentFinder, String requireSalary, String workExperience, String specialtyId) throws ServiceException {
        SpecialtyService specialtyService = SpecialtyServiceImpl.getInstance();
        Optional<Finder> finder = Optional.empty();
        Optional<String> errorMessage = Optional.empty();
        Optional<Specialty> newSpecialty;
        Map<Optional<Finder>, Optional<String>> editResult = new HashMap<>();
        if (!UserInputValidator.isValidSalary(requireSalary) || !UserInputValidator.isValidWorkExperience(workExperience) || !UserInputValidator.isValidId(specialtyId)) {
            errorMessage = Optional.of(ErrorMessage.ERROR_EDIT_FINDER_INFO);
        } else {
            try {
                newSpecialty = specialtyService.findById(Integer.parseInt(specialtyId));
                if (newSpecialty.isPresent()) {
                    finder = Optional.of(new Finder(currentFinder.getEntityId(), new BigDecimal(requireSalary),
                            Integer.valueOf(workExperience), newSpecialty.get(), currentFinder.getWorkStatus()));
                    if (!finder.get().equals(currentFinder)) {
                        finderDao.update(finder.get());
                    } else {
                        errorMessage = Optional.of(ErrorMessage.IDENTICAL_EDIT_FINDER_INFO);
                    }
                } else {
                    errorMessage = Optional.of(ErrorMessage.NO_SUCH_SPECIALTY);
                }
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        editResult.put(finder, errorMessage);
        return editResult;
    }

    @Override
    public Map<Optional<Finder>, Optional<String>> findFinderById(String finderId) throws ServiceException {
        Optional<Finder> foundFinder = Optional.empty();
        Optional<String> errorMessage = Optional.empty();
        Map<Optional<Finder>, Optional<String>> findResult = new HashMap<>();
        if (!UserInputValidator.isValidId(finderId)) {
            errorMessage = Optional.of(ErrorMessage.INCORRECT_FIND_FINDER_PARAMETERS);
        } else {
            try {
                foundFinder = finderDao.findById(Integer.parseInt(finderId));
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        findResult.put(foundFinder, errorMessage);
        return findResult;
    }

    @Override
    public Optional<Finder> findById(Integer finderId) throws ServiceException {
        Optional<Finder> foundFinder;
        try {
            foundFinder = finderDao.findById(finderId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return foundFinder;
    }

    @Override
    public void update(Finder finder) throws ServiceException {
        try {
            finderDao.update(finder);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateFinderWorkStatus(String companyName, Integer finderId) throws ServiceException {
        try {
            finderDao.updateFinderWorkStatus(companyName, finderId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean existsFinder(Integer finderId) throws ServiceException {
        boolean isExist;
        try {
            isExist = finderDao.isExistsFinder(finderId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return isExist;
    }

    @Override
    public Optional<String> findFinderLogin(Integer finderId) throws ServiceException {
        Optional<String> finderLogin;
        try {
            finderLogin = finderDao.findFinderLogin(finderId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return finderLogin;
    }

    @Override
    public List<Finder> findAll(int start, int end) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Integer entityId) throws ServiceException {
        throw new UnsupportedOperationException();
    }
}