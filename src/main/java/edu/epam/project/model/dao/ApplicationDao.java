package edu.epam.project.model.dao;

import edu.epam.project.exception.DaoException;
import edu.epam.project.model.entity.Application;

import java.util.Map;

public interface ApplicationDao extends BaseDao<Integer, Application> {
    boolean isFinderApply(Integer vacancyId, Integer finderId) throws DaoException;

    int countApplications() throws DaoException;

    boolean isExistsApplicationId(Integer applicationId) throws DaoException;

    Map<Integer, Integer> findPairVacancyAndFinderId(Integer applicationId) throws DaoException;
}