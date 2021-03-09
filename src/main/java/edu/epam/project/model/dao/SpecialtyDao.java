package edu.epam.project.model.dao;

import edu.epam.project.exception.DaoException;
import edu.epam.project.model.entity.Specialty;

import java.util.List;

public interface SpecialtyDao extends BaseDao<Integer, Specialty> {
    boolean isExistsSpecialtyName(String specialtyName) throws DaoException;

    List<Specialty> findAllSpecialties() throws DaoException;
}