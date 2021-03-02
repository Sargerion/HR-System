package edu.epam.project.model.service;

import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Hr;
import edu.epam.project.model.entity.Vacancy;

import java.util.Map;
import java.util.Optional;

public interface HrService extends BaseService<Integer, Hr>{

    Map<Optional<Vacancy>, Optional<String>> addVacancy(String vacancyName, String specialtyId, String requireSalary, String workExperience) throws ServiceException;

}