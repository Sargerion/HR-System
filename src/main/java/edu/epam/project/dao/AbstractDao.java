package edu.epam.project.dao;

import edu.epam.project.builder.EntityBuilder;
import edu.epam.project.entity.Entity;
import edu.epam.project.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<K, T extends Entity> implements AutoCloseable {

    protected Connection connection;

    protected AbstractDao() {

    }

    protected AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public abstract boolean add(T entity) throws DaoException;

    public abstract Optional<T> findById(K entityId) throws DaoException;

    public abstract List<T> findAll() throws DaoException;

    public abstract boolean update(T entity, K entityId) throws DaoException;

    public abstract boolean delete(T entity) throws DaoException;

    public abstract boolean deleteById(K entityId) throws DaoException;

    @Override
    public void close() throws DaoException {
        if (connection != null) {
            try {
                if (!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }//todo спросить
}