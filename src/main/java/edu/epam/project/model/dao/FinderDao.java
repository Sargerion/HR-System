package edu.epam.project.model.dao;

import edu.epam.project.exception.DaoException;
import edu.epam.project.model.entity.Finder;

import java.util.Optional;

public interface FinderDao extends BaseDao<Integer, Finder> {
    boolean isExistsFinder(Integer finderId) throws DaoException;

    Optional<String> findFinderLogin(Integer finderId) throws DaoException;

    void updateFinderWorkStatus(String companyName, Integer finderId) throws DaoException;
}