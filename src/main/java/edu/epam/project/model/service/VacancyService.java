package edu.epam.project.model.service;

import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Vacancy;

import java.util.Map;
import java.util.Optional;

/**
 * The interface VacancyService used to give functionality to Vacancy object and interact with dao.
 * @author Sargerion.
 */
public interface VacancyService extends BaseService<Integer, Vacancy> {
    /**
     * Add vacancy.
     *
     * @param hrLogin         the hr login.
     * @param vacancyName     the vacancy name.
     * @param specialtyId     the specialty id.
     * @param requireSalary   the require salary.
     * @param workExperience  the work experience.
     * @param isVacancyActive boolean, which means is vacancy active.
     * @return the map of optional Vacancy object and optional of String object, which describe error message.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Map<Optional<Vacancy>, Optional<String>> addVacancy(String hrLogin, String vacancyName, String specialtyId, String requireSalary, String workExperience, boolean isVacancyActive) throws ServiceException;

    /**
     * Update vacancy status.
     *
     * @param isActive  boolean, which describe vacancy status.
     * @param vacancyId the vacancy id.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    void updateVacancyStatus(boolean isActive, Integer vacancyId) throws ServiceException;

    /**
     * Count vacancies int.
     *
     * @return the int, which means vacancies count.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    int countVacancies() throws ServiceException;
}