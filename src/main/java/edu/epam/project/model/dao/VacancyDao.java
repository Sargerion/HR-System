package edu.epam.project.model.dao;

import edu.epam.project.exception.DaoException;
import edu.epam.project.model.entity.Vacancy;

/**
 * The interface VacancyDao used to interact with database.
 * @author Sargerion.
 */
public interface VacancyDao extends BaseDao<Integer, Vacancy> {
    /**
     * Update vacancy status.
     *
     * @param isActive  boolean, which means is vacancy active.
     * @param vacancyId the vacancy id.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    void updateVacancyStatus(boolean isActive, Integer vacancyId) throws DaoException;

    /**
     * Count vacancies int.
     *
     * @return  the int, which means vacancies count.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    int countVacancies() throws DaoException;
}