package edu.epam.project.dao;

import edu.epam.project.entity.User;
import edu.epam.project.exception.DaoException;

import java.util.List;
import java.util.Optional;

public abstract class AdminDao extends AbstractDao<Integer, User> {

    public abstract List<User> findNotActiveHRs() throws DaoException;

}
