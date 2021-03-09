package edu.epam.project.model.service;

import edu.epam.project.model.entity.Entity;
import edu.epam.project.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface BaseService<K, T extends Entity> {
    void add(T entity) throws ServiceException;

    List<T> findAll(int start, int end) throws ServiceException;

    Optional<T> findById(K entityId) throws ServiceException;

    void update(T entity) throws ServiceException;

    void deleteById(K entityId) throws ServiceException;
}