package edu.epam.project.dao.impl;

import edu.epam.project.builder.EntityBuilder;
import edu.epam.project.builder.impl.UserBuilder;
import edu.epam.project.dao.AdminDao;
import edu.epam.project.entity.User;
import edu.epam.project.entity.UserStatus;
import edu.epam.project.entity.UserType;
import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminDaoImpl implements AdminDao {

    private static final AdminDaoImpl instance = new AdminDaoImpl();
    private static final Logger logger = LogManager.getLogger();
    private static final EntityBuilder<User> userBuilder = new UserBuilder();

    private AdminDaoImpl() {
    }

    public static AdminDaoImpl getInstance() {
        return instance;
    }

    @Language("SQL")
    private static final String SELECT_NOT_ACTIVE_HR_LIST = "SELECT U.user_id, U.user_login, U.user_password, U.user_email, UT.user_type_name, " +
            "US.user_status_name FROM users U INNER JOIN user_types UT ON U.user_type_id = UT.user_type_id " +
            "INNER JOIN user_statuses US ON U.user_status_id = US.user_status_id WHERE UT.user_type_name = ? " +
            "AND US.user_status_name = ?;";

    @Override
    public boolean add(User entity) throws DaoException {
        return false;
    }

    @Override
    public Optional<User> findById(Integer entityId) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public void update(User entity) throws DaoException {

    }

    @Override
    public boolean delete(User entity) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Integer entityId) throws DaoException {
        return false;
    }

    @Override
    public List<User> findNotActiveHRs() throws DaoException {
        List<User> notActiveHRList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NOT_ACTIVE_HR_LIST)) {
            preparedStatement.setString(1, UserType.COMPANY_HR.toString());
            preparedStatement.setString(2, UserStatus.NOT_ACTIVE.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = userBuilder.build(resultSet);
                notActiveHRList.add(user);
            }
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return notActiveHRList;
    }
}