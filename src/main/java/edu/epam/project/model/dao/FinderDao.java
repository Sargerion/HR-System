package edu.epam.project.model.dao;

import edu.epam.project.exception.DaoException;
import edu.epam.project.model.entity.Finder;

public interface FinderDao extends BaseDao<Integer, Finder> {
    boolean existsFinder(Integer finderId) throws DaoException;
}