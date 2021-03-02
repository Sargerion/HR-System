package edu.epam.project.model.service.impl;

import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.dao.HrDao;
import edu.epam.project.model.dao.impl.HrDaoImpl;
import edu.epam.project.model.entity.Hr;
import edu.epam.project.model.entity.Vacancy;
import edu.epam.project.model.service.HrService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HrServiceImpl implements HrService {

    private static final HrServiceImpl instance = new HrServiceImpl();
    private static final Logger logger = LogManager.getLogger();
    private final HrDao hrDao = HrDaoImpl.getInstance();

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
    public Map<Optional<Vacancy>, Map<Optional<String>, Optional<String>>> addVacancy(String vacancyName, String specialtyId, String requireSalary, String workExperience) throws ServiceException {
        return null;
    }
}
