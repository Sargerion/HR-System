package edu.epam.project.model.dao;

import edu.epam.project.exception.ConnectionException;
import edu.epam.project.model.entity.Entity;
import edu.epam.project.exception.DaoException;
import edu.epam.project.model.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BaseDao<K, T extends Entity> {
    void add(T entity) throws DaoException;

    Optional<T> findById(K entityId) throws DaoException;

    List<T> findAll(int start, int end) throws DaoException;

    void update(T entity) throws DaoException;

    void deleteById(K entityId) throws DaoException;

    default boolean isExistsId(Integer value, String sqlQuery) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt(1) != 0;
        } catch (ConnectionException | SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    default boolean isExistsStringValue(String value, String sqlQuery) throws DaoException {
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

    default int countEntities(String sqlQuery) throws DaoException {
        int entitiesCount;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            entitiesCount = resultSet.getInt(1);
        } catch (ConnectionException | SQLException e) {
            throw new DaoException(e);
        }
        return entitiesCount;
    }
}