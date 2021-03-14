package edu.epam.project.controller.command.impl.admin;

import edu.epam.project.controller.command.*;
import edu.epam.project.model.entity.User;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.service.UserService;
import edu.epam.project.model.service.impl.UserServiceImpl;
import edu.epam.project.tag.ViewAllUsersTag;
import edu.epam.project.model.util.message.FriendlyMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * The implementation of Command interface for all users finding by admin user type.
 * @author Sargerion.
 */
public class FindUserListCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        Integer currentTablePage = (Integer) requestContext.getSessionAttribute(SessionAttribute.ALL_USERS_CURRENT_PAGE);
        Optional<String> nextPage = requestContext.getRequestParameter(RequestParameter.NEW_PAGE);
        if (nextPage.isEmpty() && currentTablePage == null) {
            currentTablePage = 1;
        } else if (nextPage.isPresent()) {
            currentTablePage = Integer.parseInt(nextPage.get());
        }
        requestContext.setSessionAttribute(SessionAttribute.ALL_USERS_CURRENT_PAGE, currentTablePage);
        int start = (currentTablePage - 1) * ViewAllUsersTag.LIST_LINES_COUNT;
        int end = ViewAllUsersTag.LIST_LINES_COUNT + start;
        UserService userService = UserServiceImpl.getInstance();
        try {
            List<User> allUsers = userService.findAll(start, end);
            requestContext.setSessionAttribute(SessionAttribute.ALL_USERS_LIST, allUsers);
            int usersCount = userService.countUsers();
            requestContext.setSessionAttribute(SessionAttribute.USERS_COUNT, usersCount);
            if (allUsers.size() == 0) {
                requestContext.setRequestAttribute(RequestAttribute.CONFIRM_MESSAGE, FriendlyMessage.EMPTY_USER_LIST);
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
        return new CommandResult(PathJsp.ALL_USERS, TransitionType.FORWARD);
    }
}