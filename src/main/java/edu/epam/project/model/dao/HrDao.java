package edu.epam.project.model.dao;

import edu.epam.project.exception.DaoException;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.Vacancy;

public interface HrDao extends BaseDao<Integer, User>{
    void addVacancy(Vacancy vacancy) throws DaoException;

    String findCompanyNameByLogin(String hrLogin) throws DaoException;
}