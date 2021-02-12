package edu.epam.project.dao;

import edu.epam.project.entity.Entity;
import edu.epam.project.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BaseDao<K, T extends Entity> {
    boolean add(T entity) throws DaoException;

    Optional<T> findById(K entityId) throws DaoException;

    List<T> findAll() throws DaoException;

    void update(T entity) throws DaoException;

    boolean delete(T entity) throws DaoException;

    boolean deleteById(K entityId) throws DaoException;
}