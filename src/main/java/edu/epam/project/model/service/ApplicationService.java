package edu.epam.project.model.service;

import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Application;

import java.util.Map;
import java.util.Optional;

public interface ApplicationService extends BaseService<Integer, Application> {
    Map<Optional<Application>, Optional<String>> buildApplication(String vacancyId, Integer finderId) throws ServiceException;

    boolean isFinderApply(Integer vacancyId, Integer finderId) throws ServiceException;

    int countApplications() throws ServiceException;

    Map<Integer, Optional<String>> confirmApplication(String applicationId) throws ServiceException;

    Map<Integer, Optional<String>> rejectApplication(String applicationId) throws ServiceException;
}