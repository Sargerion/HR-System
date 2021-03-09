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

public class FinderServiceImpl implements FinderService {

    private static final FinderServiceImpl instance = new FinderServiceImpl();
    private static final Logger logger = LogManager.getLogger();
    private final FinderDao finderDao = new FinderDaoImpl();

    private FinderServiceImpl() {
    }

    public static FinderServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void add(Finder entity) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Finder> findAll(int start, int end) throws ServiceException {
        throw new UnsupportedOperationException();
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
    public void deleteById(Integer entityId) throws ServiceException {
        throw new UnsupportedOperationException();
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
}