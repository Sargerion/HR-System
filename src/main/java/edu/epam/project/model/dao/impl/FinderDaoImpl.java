package edu.epam.project.model.dao.impl;

import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.model.dao.FinderDao;
import edu.epam.project.model.dao.builder.EntityBuilder;
import edu.epam.project.model.dao.builder.impl.FinderBuilder;
import edu.epam.project.model.dao.table.FindersColumn;
import edu.epam.project.model.dao.table.UsersColumn;
import edu.epam.project.model.entity.Finder;
import edu.epam.project.model.pool.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class FinderDaoImpl implements FinderDao {

    private static final Logger logger = LogManager.getLogger();
    private final EntityBuilder<Finder> finderBuilder = new FinderBuilder();

    @Language("SQL")
    private static final String INSERT_FINDER = "INSERT INTO finders (finder_id, finder_require_salary, finder_work_experience, finder_specialty_id) " +
            "VALUES (?, ?, ?, ?);";

    @Language("SQL")
    private static final String UPDATE_FINDER = "UPDATE finders SET finder_require_salary = ?, finder_work_experience = ?, finder_specialty_id = ? WHERE finder_id = ?";

    @Language("SQL")
    private static final String CONTAINS_FINDER_ID = "SELECT EXISTS(SELECT finder_id FROM finders WHERE finder_id = ?) AS finder_existence;";

    @Language("SQL")
    private static final String SELECT_FINDER_BY_ID = "SELECT finder_id, finder_require_salary, finder_work_experience, specialty_id, specialty_name, finder_work_status " +
            "FROM finders INNER JOIN specialties ON finders.finder_specialty_id = specialties.specialty_id WHERE finder_id = ?;";

    @Language("SQL")
    private static final String SELECT_FINDER_LOGIN_BY_USER_ID = "SELECT user_login FROM users WHERE user_id = ?;";

    @Language("SQL")
    private static final String UPDATE_FINDER_WORK_STATUS = "UPDATE finders SET finder_work_status = ? WHERE finder_id = ?;";

    @Override
    public void add(Finder finder) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FINDER)) {
            preparedStatement.setInt(1, finder.getEntityId());
            preparedStatement.setBigDecimal(2, finder.getRequireSalary());
            preparedStatement.setInt(3, finder.getWorkExperience());
            preparedStatement.setInt(4, finder.getSpecialty().getEntityId());
            preparedStatement.executeUpdate();
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Finder> findById(Integer entityId) throws DaoException {
        Optional<Finder> foundFinder = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FINDER_BY_ID)) {
            preparedStatement.setInt(1, entityId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                foundFinder = Optional.of(finderBuilder.build(resultSet));
            }
            if (foundFinder.isPresent()) {
                foundFinder.get().setFinderWorkStatus(resultSet.getString(FindersColumn.WORK_STATUS));
            }
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return foundFinder;
    }

    @Override
    public void update(Finder finder) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FINDER)) {
            preparedStatement.setBigDecimal(1, finder.getRequireSalary());
            preparedStatement.setInt(2, finder.getWorkExperience());
            preparedStatement.setInt(3, finder.getSpecialty().getEntityId());
            preparedStatement.setInt(4, finder.getEntityId());
            preparedStatement.executeUpdate();
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public void updateFinderWorkStatus(String companyName, Integer finderId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FINDER_WORK_STATUS)) {
            preparedStatement.setString(1, companyName);
            preparedStatement.setInt(2, finderId);
            preparedStatement.executeUpdate();
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<String> findFinderLogin(Integer finderId) throws DaoException {
        Optional<String> finderLogin = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FINDER_LOGIN_BY_USER_ID)) {
            preparedStatement.setInt(1, finderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                finderLogin = Optional.of(resultSet.getString(UsersColumn.LOGIN));
            }
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return finderLogin;
    }

    @Override
    public boolean isExistsFinder(Integer finderId) throws DaoException {
        return isExistsId(finderId, CONTAINS_FINDER_ID);
    }

    @Override
    public List<Finder> findAll(int start, int end) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Integer entityId) throws DaoException {
        throw new UnsupportedOperationException();
    }
}