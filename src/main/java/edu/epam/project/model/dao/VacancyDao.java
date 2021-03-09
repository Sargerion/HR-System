package edu.epam.project.model.dao;

import edu.epam.project.exception.DaoException;
import edu.epam.project.model.entity.Vacancy;

public interface VacancyDao extends BaseDao<Integer, Vacancy> {
    void updateVacancyStatus(boolean isActive, Integer vacancyId) throws DaoException;

    int countVacancies() throws DaoException;
}