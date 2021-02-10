package edu.epam.project.dao.impl;

import edu.epam.project.builder.EntityBuilder;
import edu.epam.project.builder.impl.UserBuilder;
import edu.epam.project.dao.UserDao;
import edu.epam.project.dao.table.UserStatusesColumn;
import edu.epam.project.dao.table.UserTypesColumn;
import edu.epam.project.dao.table.UsersColumn;
import edu.epam.project.entity.User;
import edu.epam.project.entity.UserStatus;
import edu.epam.project.entity.UserType;
import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UsersDaoImpl extends UserDao {

    private static final Logger logger = LogManager.getLogger();
    private static final EntityBuilder<User> userBuilder = UserBuilder.INSTANCE;

    @Language("SQL")
    private static final String CONTAINS_USER_ID = "SELECT EXISTS(SELECT user_id FROM users WHERE user_id = ?) AS user_existence;";

    @Language("SQL")
    private static final String CONTAINS_USER_LOGIN = "SELECT EXISTS(SELECT user_login FROM users WHERE user_login = ?) AS user_existence;";

    @Language("SQL")
    private static final String SELECT_PASSWORD_BY_LOGIN = "SELECT user_password FROM users WHERE user_login = ?;";

    @Language("SQL")
    private static final String SELECT_USER_BY_LOGIN = "SELECT U.user_id, U.user_login, U.user_password, U.user_email, UT.user_type_name, " +
            "US.user_status_name FROM users U INNER JOIN user_types UT ON U.user_type_id = UT.user_type_id " +
            "INNER JOIN user_statuses US ON U.user_status_id = US.user_status_id WHERE U.user_login = ?;";

    @Language("SQL")
    private static final String SELECT_USER_BY_ID = "SELECT U.user_id, U.user_login, U.user_password, U.user_email, UT.user_type_name, " +
            "US.user_status_name FROM users U INNER JOIN user_types UT ON U.user_type_id = UT.user_type_id " +
            "INNER JOIN user_statuses US ON U.user_status_id = US.user_status_id WHERE U.user_id = ?;";

    @Language("SQL")
    private static final String SELECT_USER_TOKEN = "SELECT confirmation_token FROM users WHERE user_id=?;";

    @Language("SQL")
    private static final String INSERT_USER = "INSERT INTO users (user_login, user_password, user_email, user_type_id, user_status_id, confirmation_token) " +
            "VALUES (?, ?, ?, ?, ?, ?);";

    @Language("SQL")
    private static final String UPDATE_USER = "UPDATE users SET user_login = ?, user_email, user_type_id, user_status_id;";

    @Language("SQL")
    private static final String UPDATE_STATUS = "UPDATE users SET user_status_id=?;";

    @Language("SQL")
    private static final String SELECT_USER_STATUS_BY_LOGIN = "SELECT user_status_name FROM users INNER JOIN user_statuses " +
            "ON users.user_status_id = user_statuses.user_status_id WHERE user_login = ?;";


    @Override
    public boolean add(User entity) throws DaoException {
        throw new UnsupportedOperationException("You can't add user in UsersDaoImpl by this way");
    }

    @Override
    public Optional<User> addUser(User user, String encryptedPassword) throws DaoException {
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
            user.setUserId(resultSet.getInt(1));
        } catch (ConnectionException | SQLException e) {
            throw new DaoException(e);
        }
        return Optional.of(user);
    }

    @Override
    public Optional<String> findUserActivateTokenById(Integer id) throws DaoException {
        Optional<String> foundToken;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_TOKEN)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            foundToken = Optional.ofNullable(resultSet.getString(UsersColumn.CONFIRMATION_TOKEN));
        } catch (ConnectionException | SQLException e) {
            throw new DaoException(e);
        }
        return foundToken;
    }

    @Override
    public boolean exists(Integer userId) throws DaoException {
        boolean isExist;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CONTAINS_USER_ID)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            isExist = resultSet.getInt(1) != 0;
        } catch (ConnectionException | SQLException e) {
            throw new DaoException(e);
        }
        return isExist;
    }

    @Override
    public boolean existsLogin(String userLogin) throws DaoException {
        boolean isExists = isExist(userLogin, CONTAINS_USER_LOGIN);
        return isExists;
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
            throw new DaoException(e);
        }
        return foundUser;
    }

    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(User user) throws DaoException {
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
//            preparedStatement.setString(1, user.getLogin());
//            preparedStatement.setString(2, user.getEmail());
//            preparedStatement.setInt(4, user.getType());
//            preparedStatement.setInt(5, UserStatusesColumn.NOT_ACTIVE);
//        } catch (ConnectionException | SQLException e) {
//            throw new DaoException(e);
//        }
        return false;
    }

    @Override
    public void updateStatus(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATUS)) {
            switch (user.getStatus()) {
                case NOT_ACTIVE -> {
                    preparedStatement.setInt(1, UserStatusesColumn.ACTIVE);
                }
                case BLOCKED -> {
                    preparedStatement.setInt(1, UserStatusesColumn.NOT_ACTIVE);
                }
                default -> {
                    preparedStatement.setInt(1, UserStatusesColumn.BLOCKED);
                }
            }
            preparedStatement.executeUpdate();
        } catch (ConnectionException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(User entity) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Integer entityId) throws DaoException {
        return false;
    }

    private boolean isExist(String value, String sqlQuery) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt(1) != 0;
        } catch (ConnectionException | SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }
}