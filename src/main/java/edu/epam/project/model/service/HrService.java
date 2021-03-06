package edu.epam.project.model.service;

import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Company;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.Vacancy;

import java.util.Map;
import java.util.Optional;

public interface HrService extends BaseService<Integer, User>{

    Map<Optional<Vacancy>, Optional<String>> addVacancy(String hrLogin, String vacancyName, String specialtyId, String requireSalary, String workExperience, boolean isVacancyActive) throws ServiceException;

    String findCompanyNameByHrLogin(String hrLogin) throws ServiceException;
}