package edu.epam.project.service;

import edu.epam.project.entity.User;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.util.mail.ConfirmationToken;

import java.util.Optional;

public interface UserService extends BaseService<Integer, User> {

    Optional<User> loginUser(String login, String password) throws ServiceException;

    Optional<User> registerUser(String login, String password, String repeatPassword, String email, boolean isHR) throws ServiceException;

    boolean activateUser(User user, String confirmationToken) throws ServiceException;

    Optional<String> findUserActivateTokenById(Integer id) throws ServiceException;

}
