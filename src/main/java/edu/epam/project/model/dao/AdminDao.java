package edu.epam.project.model.dao;

import edu.epam.project.model.entity.User;
import edu.epam.project.exception.DaoException;

import java.util.List;

public interface AdminDao extends BaseDao<Integer, User> {

    List<User> findNotActiveHRs() throws DaoException;

    int countUsers() throws DaoException;
}