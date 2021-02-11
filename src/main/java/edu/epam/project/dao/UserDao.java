package edu.epam.project.dao;

import edu.epam.project.entity.User;
import edu.epam.project.entity.UserStatus;
import edu.epam.project.exception.DaoException;

import java.util.Optional;

public abstract class UserDao extends AbstractDao<Integer, User> {

    public abstract Optional<User> addUser(User user, String encryptedPassword) throws DaoException;

    public abstract Optional<User> findUserByLogin(String login) throws DaoException;

    public abstract UserStatus detectUserStatusByLogin(String login) throws DaoException;

    public abstract void updateStatus(User user) throws DaoException;

    public abstract boolean existId(Integer userId) throws DaoException;

    public abstract boolean existsLogin(String userLogin) throws DaoException;

    public abstract Optional<String> findUserPasswordByLogin(String login) throws DaoException;

    public abstract Optional<String> findUserActivateTokenById(Integer id) throws DaoException;
}
