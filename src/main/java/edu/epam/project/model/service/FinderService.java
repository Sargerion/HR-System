package edu.epam.project.model.service;

import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Finder;

import java.util.Map;
import java.util.Optional;

/**
 * The interface FinderService used to give functionality to Finder object and interact with dao.
 * @author Sargerion.
 */
public interface FinderService extends BaseService<Integer, Finder> {
    /**
     * Add finder.
     *
     * @param finderId       the finder id.
     * @param requireSalary  the require salary.
     * @param workExperience the work experience.
     * @param specialtyId    the specialty id.
     * @return the map of optional Finder object and optional of String object, which describe error message.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Map<Optional<Finder>, Optional<String>> addFinder(Integer finderId, String requireSalary, String workExperience, String specialtyId) throws ServiceException;

    /**
     * Exists finder boolean.
     *
     * @param finderId the finder id.
     * @return the boolean, which means finder existence.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    boolean existsFinder(Integer finderId) throws ServiceException;

    /**
     * Find finder login optional.
     *
     * @param finderId the finder id.
     * @return the optional  of String object, which describe finder login.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Optional<String> findFinderLogin(Integer finderId) throws ServiceException;

    /**
     * Update finder work status, sets companyName as workplace.
     *
     * @param companyName the company name.
     * @param finderId    the finder id.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    void updateFinderWorkStatus(String companyName, Integer finderId) throws ServiceException;

    /**
     * Edit info.
     *
     * @param currentFinder  the current finder.
     * @param requireSalary  the require salary.
     * @param workExperience the work experience.
     * @param specialtyId    the specialty id.
     * @return the map of optional Finder object and optional of String object, which describe error message.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Map<Optional<Finder>, Optional<String>> editInfo(Finder currentFinder, String requireSalary, String workExperience, String specialtyId) throws ServiceException;

    /**
     * Find finder by id.
     *
     * @param finderId the finder id.
     * @return the map of optional Finder object and optional of String object, which describe error message.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Map<Optional<Finder>, Optional<String>> findFinderById(String finderId) throws ServiceException;
}