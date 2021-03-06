package edu.epam.project.model.service;

import edu.epam.project.model.entity.Company;
import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.entity.User;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Vacancy;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService extends BaseService<Integer, User> {
    Map<Optional<User>, Map<Optional<String>, Optional<String>>> loginUser(String login, String password) throws ServiceException;

    Map<Optional<User>, Map<List<String>, Map<String, String>>> registerUser(String login, String password, String repeatPassword, String email, boolean isHR) throws ServiceException;

    void updateAvatar(User user) throws ServiceException;

    Optional<String> findUserAvatar(User user) throws ServiceException;

    List<Specialty> findAllSpecialties() throws ServiceException;

    Specialty findSpecialtyById(Integer specialtyId) throws ServiceException;

    Vacancy findVacancyById(Integer vacancyId) throws ServiceException;

    List<Vacancy> findAllVacancies() throws ServiceException;

    List<Vacancy> findAllVacancies(int start, int end) throws ServiceException;

    int countVacancies() throws ServiceException;

    Company findCompanyById(Integer companyId) throws ServiceException;

    Company findCompanyByHrLogin(String hrLogin) throws ServiceException;
}