package edu.epam.project.service;

import edu.epam.project.entity.Entity;
import edu.epam.project.exception.DaoException;
import edu.epam.project.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface BaseService<K, T extends Entity> {

    boolean add(T entity) throws ServiceException;

    List<T> findAll() throws ServiceException;

    Optional<T> findById(K entityId) throws ServiceException;

    boolean update(T entity, K entityId) throws ServiceException;

    boolean delete(T entity) throws ServiceException;

    boolean deleteById(K entityId) throws ServiceException;
}
