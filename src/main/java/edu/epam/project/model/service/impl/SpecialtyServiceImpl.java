package edu.epam.project.model.service.impl;

import edu.epam.project.exception.DaoException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.dao.SpecialtyDao;
import edu.epam.project.model.dao.impl.SpecialtyDaoImpl;
import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.service.SpecialtyService;
import edu.epam.project.model.util.message.ErrorMessage;
import edu.epam.project.model.validator.UserInputValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class SpecialtyServiceImpl implements SpecialtyService {

    private static final SpecialtyServiceImpl instance = new SpecialtyServiceImpl();
    private static final Logger logger = LogManager.getLogger();
    private final SpecialtyDao specialtyDao = new SpecialtyDaoImpl();

    private SpecialtyServiceImpl() {
    }

    public static SpecialtyServiceImpl getInstance() {
        return instance;
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
                if (!specialtyDao.isExistsSpecialtyName(specialtyName)) {
                    specialty = Optional.of(new Specialty(0, specialtyName.toLowerCase()));
                    specialtyDao.add(specialty.get());
                } else {
                    errorMessage = Optional.of(ErrorMessage.SPECIALTY_NAME_DUPLICATE);
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
    public List<Specialty> findAllSpecialties() throws ServiceException {
        List<Specialty> specialties;
        try {
            specialties = new ArrayList<>(specialtyDao.findAllSpecialties());
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return specialties;
    }

    @Override
    public Optional<Specialty> findById(Integer specialtyId) throws ServiceException {
        Optional<Specialty> foundSpecialty;
        try {
            foundSpecialty = specialtyDao.findById(specialtyId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return foundSpecialty;
    }

    @Override
    public List<Specialty> findAll(int start, int end) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Specialty entity) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Integer entityId) throws ServiceException {
        throw new UnsupportedOperationException();
    }
}