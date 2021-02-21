package edu.epam.project.model.dao;

import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.UserStatus;
import edu.epam.project.exception.DaoException;

import java.util.Optional;

public interface UserDao extends BaseDao<Integer, User> {

    Optional<User> addUser(User user, String encryptedPassword) throws DaoException;

    Optional<User> findUserByLogin(String login) throws DaoException;

    UserStatus detectUserStatusByLogin(String login) throws DaoException;

    void updateStatus(User user) throws DaoException;

    boolean existId(Integer userId) throws DaoException;

    boolean existsLogin(String userLogin) throws DaoException;

    Optional<String> findUserPasswordByLogin(String login) throws DaoException;

    void updateAvatar(User user) throws DaoException;

    Optional<String> findUserAvatar(User user) throws DaoException;
}