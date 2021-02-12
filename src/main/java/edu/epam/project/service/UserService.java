package edu.epam.project.service;

import edu.epam.project.entity.User;
import edu.epam.project.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService extends BaseService<Integer, User> {
    Map<Optional<User>, Map<Optional<String>, Optional<String>>> loginUser(String login, String password) throws ServiceException;

    Map<Optional<User>, Map<List<String>, Map<String, String>>> registerUser(String login, String password, String repeatPassword, String email, boolean isHR) throws ServiceException;
}