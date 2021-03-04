package edu.epam.project.model.dao;

import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.UserStatus;
import edu.epam.project.exception.DaoException;
import edu.epam.project.model.entity.Vacancy;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<Integer, User> {

    void addUser(User user, String encryptedPassword) throws DaoException;

    Optional<User> findUserByLogin(String login) throws DaoException;

    UserStatus detectUserStatusByLogin(String login) throws DaoException;

    void updateStatus(User user) throws DaoException;

    boolean existId(Integer userId) throws DaoException;

    boolean existsLogin(String userLogin) throws DaoException;

    Optional<String> findUserPasswordByLogin(String login) throws DaoException;

    void updateAvatar(User user) throws DaoException;

    Optional<String> findUserAvatar(User user) throws DaoException;

    List<Specialty> findAllSpecialties() throws DaoException;

    Specialty findSpecialtyById(Integer specialtyId) throws DaoException;

    Vacancy findVacancyById(Integer vacancyId) throws DaoException;

    List<Vacancy> findAllVacancies() throws DaoException;
}