package edu.epam.project.model.service;

import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.entity.User;
import edu.epam.project.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService extends BaseService<Integer, User> {
    Map<Optional<User>, Map<Optional<String>, Optional<String>>> loginUser(String login, String password) throws ServiceException;

    Map<Optional<User>, Map<List<String>, Map<String, String>>> registerUser(String login, String password, String repeatPassword, String email, boolean isHR) throws ServiceException;

    void updateAvatar(User user) throws ServiceException;

    Optional<String> findUserAvatar(User user) throws ServiceException;

    List<Specialty> findAllSpecialties() throws ServiceException;

    Optional<Specialty> findSpecialtyByName(String specialtyName) throws ServiceException;

    Specialty findSpecialtyById(Integer specialtyId) throws ServiceException;
}