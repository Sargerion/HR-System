package edu.epam.project.model.dao;

import edu.epam.project.exception.DaoException;
import edu.epam.project.model.entity.Finder;

import java.util.Optional;

/**
 * The interface FinderDao used to interact with database.
 * @author Sargerion.
 */
public interface FinderDao extends BaseDao<Integer, Finder> {
    /**
     * Is exists finder boolean.
     *
     * @param finderId the finder id.
     * @return the boolean, which means finder existence.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    boolean isExistsFinder(Integer finderId) throws DaoException;

    /**
     * Find finder login optional.
     *
     * @param finderId the finder id.
     * @return the optional of finder login.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    Optional<String> findFinderLogin(Integer finderId) throws DaoException;

    /**
     * Updates finder work status.
     *
     * @param companyName the company name.
     * @param finderId    the finder id.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    void updateFinderWorkStatus(String companyName, Integer finderId) throws DaoException;
}