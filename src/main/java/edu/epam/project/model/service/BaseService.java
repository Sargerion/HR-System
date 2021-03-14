package edu.epam.project.model.service;

import edu.epam.project.model.entity.Entity;
import edu.epam.project.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The interface BaseService used to give functionality system entities and interact with dao.
 *
 * @param <K> the type Entity id.
 * @param <T> the type of Entity.
 *
 * @author Sargerion.
 */
public interface BaseService<K, T extends Entity> {
    /**
     * Find all entities list.
     *
     * @param start the value that means from which table row start finding.
     * @param end   the value that means on which table row end finding.
     * @return the list of entities.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    List<T> findAll(int start, int end) throws ServiceException;

    /**
     * Find by id optional of entity.
     *
     * @param entityId the entity id.
     * @return the optional of entity.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Optional<T> findById(K entityId) throws ServiceException;

    /**
     * Update the entity.
     *
     * @param entity the entity.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    void update(T entity) throws ServiceException;

    /**
     * Delete by entity id.
     *
     * @param entityId the entity id.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    void deleteById(K entityId) throws ServiceException;
}