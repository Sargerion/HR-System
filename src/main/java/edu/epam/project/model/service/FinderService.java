package edu.epam.project.model.service;

import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Finder;

import java.util.Map;
import java.util.Optional;

public interface FinderService extends BaseService<Integer, Finder> {
    Map<Optional<Finder>, Optional<String>> addFinder(Integer finderId, String requireSalary, String workExperience, String specialtyId) throws ServiceException;

    boolean existsFinder(Integer finderId) throws ServiceException;

    Optional<String> findFinderLogin(Integer finderId) throws ServiceException;

    void updateFinderWorkStatus(String companyName, Integer finderId) throws ServiceException;

    Map<Optional<Finder>, Optional<String>> editInfo(Finder currentFinder, String requireSalary, String workExperience, String specialtyId) throws ServiceException;

    Map<Optional<Finder>, Optional<String>> findFinderById(String finderId) throws ServiceException;
}