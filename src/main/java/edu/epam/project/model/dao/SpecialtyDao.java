package edu.epam.project.model.dao;

import edu.epam.project.exception.DaoException;
import edu.epam.project.model.entity.Specialty;

import java.util.List;

/**
 * The interface SpecialtyDao used to interact with database.
 * @author Sargerion.
 */
public interface SpecialtyDao extends BaseDao<Integer, Specialty> {
    /**
     * Is exists specialty name boolean.
     *
     * @param specialtyName the specialty name.
     * @return the boolean, which means specialty name existence.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    boolean isExistsSpecialtyName(String specialtyName) throws DaoException;

    /**
     * Find all specialties list.
     *
     * @return the list of specialties.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    List<Specialty> findAllSpecialties() throws DaoException;
}