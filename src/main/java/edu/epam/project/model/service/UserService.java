package edu.epam.project.model.service;

import edu.epam.project.model.entity.User;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.UserStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface UserService used to give functionality to User object and interact with dao.
 * @author Sargerion.
 */
public interface UserService extends BaseService<Integer, User> {
    /**
     * Login user.
     *
     * @param login    the user login.
     * @param password the user password.
     * @return the map of optional User object and map of User optional of String object, which describe error message and optional of String object, which describe correct user login.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Map<Optional<User>, Map<Optional<String>, Optional<String>>> loginUser(String login, String password) throws ServiceException;

    /**
     * Register user.
     *
     * @param login          the user login.
     * @param password       the user password.
     * @param repeatPassword the  user repeat password.
     * @param email          the user email.
     * @param isHR           the boolean, which means trying to register like hr.
     * @return the map of optional User object and map of error messages list and map of correct fields.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Map<Optional<User>, Map<List<String>, Map<String, String>>> registerUser(String login, String password, String repeatPassword, String email, boolean isHR) throws ServiceException;

    /**
     * Count users int.
     *
     * @return the int, means users count.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    int countUsers() throws ServiceException;

    /**
     * Update user avatar.
     *
     * @param user the User object.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    void updateAvatar(User user) throws ServiceException;

    /**
     * Find user avatar.
     *
     * @param user the User object.
     * @return the optional of String object, which describe user avatar path.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Optional<String> findUserAvatar(User user) throws ServiceException;

    /**
     * Block user.
     *
     * @param userId the user id.
     * @return the optional of String object, which describe error message.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Optional<String> blockUser(String userId) throws ServiceException;

    /**
     * Unblock user optional.
     *
     * @param userId the user id.
     * @return the optional of String object, which describe error message.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Optional<String> unblockUser(String userId) throws ServiceException;

    /**
     * Detect user status by login.
     *
     * @param login the user login.
     * @return the enum type UserStatus.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    UserStatus detectUserStatusByLogin(String login) throws ServiceException;
}