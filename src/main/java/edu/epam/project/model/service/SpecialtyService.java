package edu.epam.project.model.service;

import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Specialty;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SpecialtyService extends BaseService<Integer, Specialty> {
    Map<Optional<Specialty>, Optional<String>> addSpecialty(String specialtyName) throws ServiceException;

    List<Specialty> findAllSpecialties() throws ServiceException;
}