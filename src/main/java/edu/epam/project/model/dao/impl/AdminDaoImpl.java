package edu.epam.project.model.dao.impl;

import edu.epam.project.model.dao.builder.EntityBuilder;
import edu.epam.project.model.dao.builder.impl.UserBuilder;
import edu.epam.project.model.dao.AdminDao;
import edu.epam.project.model.dao.table.UserStatusesColumn;
import edu.epam.project.model.dao.table.UserTypesColumn;
import edu.epam.project.model.entity.Company;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.UserStatus;
import edu.epam.project.model.entity.UserType;
import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.model.pool.ConnectionPool;

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
    private final EntityBuilder<User> userBuilder = new UserBuilder();

    @Language("SQL")
    private static final String SELECT_ALL_USERS_WITH_LIMIT = "SELECT U.user_id, U.user_login, U.user_email, UT.user_type_name, US.user_status_name, U.confirmation_token FROM users U " +
            "INNER JOIN user_types UT ON U.user_type_id = UT.user_type_id INNER JOIN user_statuses US ON U.user_status_id = US.user_status_id LIMIT ?, ?;";

    @Language("SQL")
    private static final String COUNT_USERS = "SELECT COUNT(*) AS users_count FROM users";

    @Language("SQL")
    private static final String SELECT_NOT_ACTIVE_HR_LIST_WITH_LIMIT = "SELECT U.user_id, U.user_login, U.user_email, UT.user_type_name, US.user_status_name, U.confirmation_token FROM users U " +
            "INNER JOIN user_types UT ON U.user_type_id = UT.user_type_id " +
            "INNER JOIN user_statuses US ON U.user_status_id = US.user_status_id WHERE UT.user_type_name = ? AND US.user_status_name = ? LIMIT ?, ?;";

    @Language("SQL")
    private static final String COUNT_NOT_ACTIVE_HRS = "SELECT COUNT(*) FROM users WHERE user_status_id = ? AND user_type_id = ?;";

    @Language("SQL")
    private static final String INSERT_COMPANY = "INSERT INTO companies (company_id, company_name, company_owner, company_addres, vacancy_id, company_hr_login) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    @Language("SQL")
    private static final String SELECT_COMPANY = "SELECT company_id, company_name, company_owner, company_addres, vacancy_name, company_hr_login FROM companies " +
            "INNER JOIN vacancies ON companies.vacancy_id = vacancies.vacancy_id WHERE company_id = ?;";

    private AdminDaoImpl() {
    }

    public static AdminDaoImpl getInstance() {
        return instance;
    }

    @Override
    public void add(User entity) throws DaoException {

    }

    @Override
    public Optional<User> findById(Integer entityId) throws DaoException {
        return Optional.empty();
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
        int usersCount;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            usersCount = resultSet.getInt(1);
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return usersCount;
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
    public List<User> findNotActiveHRs(int start, int end) throws DaoException {
        List<User> notActiveHRList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NOT_ACTIVE_HR_LIST_WITH_LIMIT)) {
            preparedStatement.setString(1, UserType.COMPANY_HR.toString());
            preparedStatement.setString(2, UserStatus.NOT_ACTIVE.toString());
            preparedStatement.setInt(3, start);
            preparedStatement.setInt(4, end);
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

    @Override
    public int countNotActiveHRs() throws DaoException {
        int notActiveHRsCount;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_NOT_ACTIVE_HRS)) {
            preparedStatement.setInt(1, UserStatusesColumn.NOT_ACTIVE);
            preparedStatement.setInt(2, UserTypesColumn.HR_TYPE);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            notActiveHRsCount = resultSet.getInt(1);
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return notActiveHRsCount;
    }

    @Override
    public void addCompany(Company company) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMPANY)) {
            preparedStatement.setInt(1, company.getEntityId());
            preparedStatement.setString(2, company.getName());
            preparedStatement.setString(3, company.getOwner());
            preparedStatement.setString(4, company.getAddress());
            preparedStatement.setInt(5, company.getVacancy().getEntityId());
            preparedStatement.setString(6, company.getHrLogin());
            preparedStatement.executeUpdate();
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
}