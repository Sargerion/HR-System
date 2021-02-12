package edu.epam.project.dao;

import edu.epam.project.entity.User;
import edu.epam.project.exception.DaoException;

import java.util.List;

public interface AdminDao extends BaseDao<Integer, User> {

    List<User> findNotActiveHRs() throws DaoException;

}