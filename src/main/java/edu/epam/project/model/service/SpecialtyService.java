package edu.epam.project.model.service;

import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Specialty;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface SpecialtyService used to give functionality to Specialty object and interact with dao.
 * @author Sargerion.
 */
public interface SpecialtyService extends BaseService<Integer, Specialty> {
    /**
     * Add specialty.
     *
     * @param specialtyName the specialty name.
     * @return the map of optional Specialty object and optional of String object, which describe error message.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Map<Optional<Specialty>, Optional<String>> addSpecialty(String specialtyName) throws ServiceException;

    /**
     * Find all specialties list.
     *
     * @return the list of specialties.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    List<Specialty> findAllSpecialties() throws ServiceException;
}