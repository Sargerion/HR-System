package edu.epam.project.model.service;

import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Vacancy;

import java.util.Map;
import java.util.Optional;

public interface VacancyService extends BaseService<Integer, Vacancy> {
    Map<Optional<Vacancy>, Optional<String>> addVacancy(String hrLogin, String vacancyName, String specialtyId, String requireSalary, String workExperience, boolean isVacancyActive) throws ServiceException;

    void updateVacancyStatus(boolean isActive, Integer vacancyId) throws ServiceException;

    int countVacancies() throws ServiceException;
}