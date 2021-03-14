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

/**
 * The interface BaseDao used to interact with database.
 *
 * @param <K> the type Entity id.
 * @param <T> the type of Entity.
 *
 * @author Sargerion.
 */
public interface BaseDao<K, T extends Entity> {
    /**
     * Add entity.
     *
     * @param entity the entity.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    void add(T entity) throws DaoException;

    /**
     * Find by id optional of entity.
     *
     * @param entityId the entity id.
     * @return the optional of entity.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    Optional<T> findById(K entityId) throws DaoException;

    /**
     * Find all entities list.
     *
     * @param start the value that means from which table row start finding.
     * @param end   the value that means on which table row end finding.
     * @return the list of entities.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    List<T> findAll(int start, int end) throws DaoException;

    /**
     * Update the entity.
     *
     * @param entity the entity.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    void update(T entity) throws DaoException;

    /**
     * Delete entity by id.
     *
     * @param entityId the entity id.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    void deleteById(K entityId) throws DaoException;

    /**
     * Is exists entity id.
     *
     * @param value    the entity id.
     * @param sqlQuery the sql query for checking id existence.
     * @return the boolean result of id existence.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
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

    /**
     * Is exists unique string entity field boolean.
     *
     * @param value    the entity unique string field.
     * @param sqlQuery the sql query for checking unique string field existence.
     * @return the boolean result of unique string field existence.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
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

    /**
     * Count entities int.
     *
     * @param sqlQuery the sql query for counting entities.
     * @return the int, which means entities count.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
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