package edu.epam.project.model.dao;

import edu.epam.project.model.entity.Company;
import edu.epam.project.model.entity.User;
import edu.epam.project.exception.DaoException;

import java.util.List;

public interface AdminDao extends BaseDao<Integer, User> {

    List<User> findNotActiveHRs(int start, int end) throws DaoException;

    int countUsers() throws DaoException;

    int countNotActiveHRs() throws DaoException;

    void addCompany(Company company) throws DaoException;
}