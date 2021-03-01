package edu.epam.project.model.dao.impl;

import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.model.dao.FinderDao;
import edu.epam.project.model.entity.Finder;
import edu.epam.project.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class FinderDaoImpl implements FinderDao {

    private static final FinderDaoImpl instance = new FinderDaoImpl();
    private static final Logger logger = LogManager.getLogger();

    @Language("SQL")
    private static final String INSERT_FINDER = "REPLACE INTO finders (finder_id, finder_require_salary, finder_work_experience, finder_specialty_id) " +
            "VALUES (?, ?, ?, ?);";

    @Language("SQL")
    private static final String CONTAINS_FINDER_ID = "SELECT EXISTS(SELECT finder_id FROM finders WHERE finder_id = ?) AS finder_existence;";

    private FinderDaoImpl() {
    }

    public static FinderDaoImpl getInstance() {
        return instance;
    }

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
        return Optional.empty();
    }

    @Override
    public List<Finder> findAll(int start, int end) throws DaoException {
        return null;
    }

    @Override
    public void update(Finder finder) throws DaoException {

    }

    @Override
    public boolean delete(Finder entity) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Integer entityId) throws DaoException {
        return false;
    }

    @Override
    public boolean existsFinder(Integer finderId) throws DaoException {
        boolean isExists = isExistID(finderId, CONTAINS_FINDER_ID);
        return isExists;
    }
}