package edu.epam.project.model.dao;

import edu.epam.project.exception.DaoException;
import edu.epam.project.model.entity.Hr;
import edu.epam.project.model.entity.Vacancy;

public interface HrDao extends BaseDao<Integer, Hr>{

    void addVacancy(Vacancy vacancy) throws DaoException;

}