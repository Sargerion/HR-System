package edu.epam.project.model.dao;

import edu.epam.project.model.entity.*;
import edu.epam.project.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * The interface UserDao used to interact with database.
 * @author Sargerion.
 */
public interface UserDao extends BaseDao<Integer, User> {
    /**
     * Count users int.
     *
     * @return the int, which means users count.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    int countUsers() throws DaoException;

    /**
     * Add user.
     *
     * @param user              the user.
     * @param encryptedPassword the encrypted password.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    void addUser(User user, String encryptedPassword) throws DaoException;

    /**
     * Find user by login optional.
     *
     * @param login the user login.
     * @return the optional of user.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    Optional<User> findUserByLogin(String login) throws DaoException;

    /**
     * Detect user status by login user status.
     *
     * @param login the user login.
     * @return the enum type user status.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    UserStatus detectUserStatusByLogin(String login) throws DaoException;

    /**
     * Update status.
     *
     * @param userStatus the enum type user status.
     * @param userId     the user id.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    void updateStatus(UserStatus userStatus, Integer userId) throws DaoException;

    /**
     * Exist id boolean.
     *
     * @param userId the user id.
     * @return the boolean result of user id existence.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    boolean existId(Integer userId) throws DaoException;

    /**
     * Exists login boolean.
     *
     * @param userLogin the user login.
     * @return the boolean result of user login existence.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    boolean existsLogin(String userLogin) throws DaoException;

    /**
     * Find user password by login optional.
     *
     * @param login the user login.
     * @return the optional of user password.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    Optional<String> findUserPasswordByLogin(String login) throws DaoException;

    /**
     * Update user avatar.
     *
     * @param user the user.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    void updateAvatar(User user) throws DaoException;

    /**
     * Find user avatar optional.
     *
     * @param user the user.
     * @return the optional string of user avatar path.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    Optional<String> findUserAvatar(User user) throws DaoException;
}