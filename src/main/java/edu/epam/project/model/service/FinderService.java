package edu.epam.project.model.service;

import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Application;
import edu.epam.project.model.entity.Finder;

import java.util.Map;
import java.util.Optional;

public interface FinderService extends BaseService<Integer, Finder> {
    Map<Optional<Finder>, Optional<String>> addFinder(Integer finderId, String requireSalary, String workExperience, String specialtyId) throws ServiceException;

    boolean existsFinder(Integer finderId) throws ServiceException;

    Map<Optional<Application>, Optional<String>> buildApplication(String vacancyId, Integer finderId, boolean isConfirmApplication) throws ServiceException;

    boolean isFinderApply(Integer finderId) throws ServiceException;
}