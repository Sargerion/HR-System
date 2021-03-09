package edu.epam.project.model.dao.impl;

import edu.epam.project.model.dao.builder.EntityBuilder;
import edu.epam.project.model.dao.builder.impl.UserBuilder;
import edu.epam.project.model.dao.UserDao;
import edu.epam.project.model.dao.table.UserStatusesColumn;
import edu.epam.project.model.dao.table.UserTypesColumn;
import edu.epam.project.model.dao.table.UsersColumn;
import edu.epam.project.model.entity.*;
import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.model.pool.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger();
    private final EntityBuilder<User> userBuilder = new UserBuilder();

    @Language("SQL")
    private static final String SELECT_ALL_USERS_WITH_LIMIT = "SELECT U.user_id, U.user_login, U.user_email, UT.user_type_name, US.user_status_name, U.confirmation_token FROM users U " +
            "INNER JOIN user_types UT ON U.user_type_id = UT.user_type_id INNER JOIN user_statuses US ON U.user_status_id = US.user_status_id LIMIT ?, ?;";

    @Language("SQL")
    private static final String COUNT_USERS = "SELECT COUNT(*) AS users_count FROM users";

    @Language("SQL")
    private static final String CONTAINS_USER_ID = "SELECT EXISTS(SELECT user_id FROM users WHERE user_id = ?) AS user_existence;";

    @Language("SQL")
    private static final String CONTAINS_USER_LOGIN = "SELECT EXISTS(SELECT user_login FROM users WHERE user_login = ?) AS user_existence;";

    @Language("SQL")
    private static final String SELECT_PASSWORD_BY_LOGIN = "SELECT user_password FROM users WHERE user_login = ?;";

    @Language("SQL")
    private static final String SELECT_USER_BY_LOGIN = "SELECT U.user_id, U.user_login, U.user_password, U.user_email, UT.user_type_name, US.user_status_name, " +
            "U.confirmation_token FROM users U " +
            "INNER JOIN user_types UT ON U.user_type_id = UT.user_type_id " +
            "INNER JOIN user_statuses US ON U.user_status_id = US.user_status_id WHERE U.user_login = ?;";

    @Language("SQL")
    private static final String SELECT_USER_BY_ID = "SELECT U.user_id, U.user_login, U.user_password, U.user_email, UT.user_type_name, US.user_status_name, " +
            "U.confirmation_token FROM users U " +
            "INNER JOIN user_types UT ON U.user_type_id = UT.user_type_id " +
            "INNER JOIN user_statuses US ON U.user_status_id = US.user_status_id WHERE U.user_id = ?;";

    @Language("SQL")
    private static final String INSERT_USER = "INSERT INTO users (user_login, user_password, user_email, user_type_id, user_status_id, confirmation_token) " +
            "VALUES (?, ?, ?, ?, ?, ?);";

    @Language("SQL")
    private static final String UPDATE_USER_BY_LOGIN = "UPDATE users SET user_login = ?, user_email = ?, user_type_id = ?, user_status_id = ?, confirmation_token = ? " +
            "WHERE user_login = ?;";

    @Language("SQL")
    private static final String UPDATE_STATUS_BY_LOGIN = "UPDATE users SET user_status_id = ? WHERE user_login = ?;";

    @Language("SQL")
    private static final String SELECT_USER_STATUS_BY_LOGIN = "SELECT user_status_name FROM users INNER JOIN user_statuses ON users.user_status_id = user_statuses.user_status_id " +
            "WHERE user_login = ?;";

    @Language("SQL")
    private static final String UPDATE_AVATAR_PATH_BY_ID = "UPDATE users SET user_avatar_path = ? WHERE user_id = ?;";

    @Language("SQL")
    private static final String SELECT_AVATAR_PATH_BY_USER_ID = "SELECT user_avatar_path FROM users WHERE user_id = ?;";

    @Override
    public void add(User entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addUser(User user, String encryptedPassword) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, encryptedPassword);
            preparedStatement.setString(3, user.getEmail());
            if (user.getType() == UserType.COMPANY_HR) {
                preparedStatement.setInt(4, UserTypesColumn.HR_TYPE);
            } else {
                preparedStatement.setInt(4, UserTypesColumn.FINDER_TYPE);
            }
            preparedStatement.setInt(5, UserStatusesColumn.NOT_ACTIVE);
            preparedStatement.setString(6, user.getConfirmationToken());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int userId = resultSet.getInt(1);
            user.setEntityId(userId);
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean existId(Integer userId) throws DaoException {
        boolean isExists = isExistsId(userId, CONTAINS_USER_ID);
        return isExists;
    }

    @Override
    public boolean existsLogin(String userLogin) throws DaoException {
        boolean isExists = isExistsStringValue(userLogin, CONTAINS_USER_LOGIN);
        return isExists;
    }

    @Override
    public void deleteById(Integer entityId) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> findUserPasswordByLogin(String login) throws DaoException {
        Optional<String> foundPassword;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PASSWORD_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            foundPassword = Optional.ofNullable(resultSet.getString(UsersColumn.PASSWORD));
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return foundPassword;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        Optional<User> checkingUser;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            checkingUser = Optional.ofNullable(userBuilder.build(resultSet));
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return checkingUser;
    }

    @Override
    public UserStatus detectUserStatusByLogin(String login) throws DaoException {
        UserStatus userStatus;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_STATUS_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            userStatus = UserStatus.valueOf(resultSet.getString(UserStatusesColumn.NAME));
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return userStatus;
    }

    @Override
    public Optional<User> findById(Integer entityId) throws DaoException {
        Optional<User> foundUser;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, entityId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            foundUser = Optional.ofNullable(userBuilder.build(resultSet));
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return foundUser;
    }

    @Override
    public List<User> findAll(int start, int end) throws DaoException {
        List<User> allUsers = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS_WITH_LIMIT)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, end);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = userBuilder.build(resultSet);
                allUsers.add(user);
            }
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return allUsers;
    }

    @Override
    public int countUsers() throws DaoException {
        int usersCount = countEntities(COUNT_USERS);
        return usersCount;
    }

    @Override
    public void update(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_BY_LOGIN)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getEmail());
            switch (user.getType()) {
                case COMPANY_HR -> {
                    preparedStatement.setInt(3, UserTypesColumn.HR_TYPE);
                }
                case FINDER -> {
                    preparedStatement.setInt(3, UserTypesColumn.FINDER_TYPE);
                }
            }
            switch (user.getStatus()) {
                case ACTIVE -> {
                    preparedStatement.setInt(4, UserStatusesColumn.ACTIVE);
                }
                case NOT_ACTIVE -> {
                    preparedStatement.setInt(4, UserStatusesColumn.NOT_ACTIVE);
                }
                case BLOCKED -> {
                    preparedStatement.setInt(4, UserStatusesColumn.BLOCKED);
                }
            }
            preparedStatement.setString(5, user.getConfirmationToken());
            preparedStatement.setString(6, user.getLogin());
            preparedStatement.executeUpdate();
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public void updateStatus(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATUS_BY_LOGIN)) {
            switch (user.getStatus()) {
                case ACTIVE -> {
                    preparedStatement.setInt(1, UserStatusesColumn.ACTIVE);
                }
                case NOT_ACTIVE -> {
                    preparedStatement.setInt(1, UserStatusesColumn.NOT_ACTIVE);
                }
                case BLOCKED -> {
                    preparedStatement.setInt(1, UserStatusesColumn.BLOCKED);
                }
            }
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.executeUpdate();
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public void updateAvatar(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AVATAR_PATH_BY_ID)) {
            preparedStatement.setString(1, user.getAvatarName());
            preparedStatement.setInt(2, user.getEntityId());
            preparedStatement.executeUpdate();
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<String> findUserAvatar(User user) throws DaoException {
        Optional<String> userAvatar;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AVATAR_PATH_BY_USER_ID)) {
            preparedStatement.setInt(1, user.getEntityId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            userAvatar = Optional.ofNullable(resultSet.getString(UsersColumn.AVATAR));
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return userAvatar;
    }
}