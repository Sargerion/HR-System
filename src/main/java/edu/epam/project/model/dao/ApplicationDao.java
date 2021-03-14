package edu.epam.project.model.dao;

import edu.epam.project.exception.DaoException;
import edu.epam.project.model.entity.Application;

import java.util.Map;

/**
 * The interface ApplicationDao used to interact with database.
 * @author Sargerion.
 */
public interface ApplicationDao extends BaseDao<Integer, Application> {
    /**
     * Is finder apply boolean.
     *
     * @param vacancyId the vacancy id.
     * @param finderId  the finder id.
     * @return the boolean, which means apply existence.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    boolean isFinderApply(Integer vacancyId, Integer finderId) throws DaoException;

    /**
     * Count applications int.
     *
     * @return the int, which means applications count.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    int countApplications() throws DaoException;

    /**
     * Is exists application id boolean.
     *
     * @param applicationId the application id.
     * @return the boolean result of application id existence.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    boolean isExistsApplicationId(Integer applicationId) throws DaoException;

    /**
     * Find pair vacancy and finder id map.
     *
     * @param applicationId the application id.
     * @return the map, which means connect between finder and vacancy by their id.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    Map<Integer, Integer> findPairVacancyAndFinderId(Integer applicationId) throws DaoException;
}