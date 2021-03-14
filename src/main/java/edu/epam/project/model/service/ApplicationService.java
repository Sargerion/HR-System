package edu.epam.project.model.service;

import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Application;

import java.util.Map;
import java.util.Optional;

/**
 * The interface ApplicationService used to give functionality to Application object and interact with dao.
 * @author Sargerion.
 */
public interface ApplicationService extends BaseService<Integer, Application> {
    /**
     * Build application.
     *
     * @param vacancyId the vacancy id.
     * @param finderId  the finder id.
     * @return the map of optional Application object and optional String object - means error message.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Map<Optional<Application>, Optional<String>> buildApplication(String vacancyId, Integer finderId) throws ServiceException;

    /**
     * Is finder apply boolean.
     *
     * @param vacancyId the vacancy id.
     * @param finderId  the finder id.
     * @return the boolean, which means finder apply existence.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    boolean isFinderApply(Integer vacancyId, Integer finderId) throws ServiceException;

    /**
     * Count applications int.
     *
     * @return the int, which means applications count.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    int countApplications() throws ServiceException;

    /**
     * Confirm application map.
     *
     * @param applicationId the application id.
     * @param noHireString  the no hire string, for checking is finder already has work.
     * @return the map of Integer - finerId and optional of String object - means error message.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Map<Integer, Optional<String>> confirmApplication(String applicationId, String noHireString) throws ServiceException;

    /**
     * Reject application map.
     *
     * @param applicationId the application id.
     * @param noHireString  the no hire string, for checking is finder already has work.
     * @return the map of Integer - finerId and optional of String object - means error message.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Map<Integer, Optional<String>> rejectApplication(String applicationId, String noHireString) throws ServiceException;
}