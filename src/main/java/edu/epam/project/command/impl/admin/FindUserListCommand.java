package edu.epam.project.command.impl.admin;

import edu.epam.project.command.*;
import edu.epam.project.entity.User;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.service.AdminService;
import edu.epam.project.service.impl.AdminServiceImpl;
import edu.epam.project.tag.ViewAllUsersTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class FindUserListCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        Integer currentTablePage = (Integer) requestContext.getSessionAttribute(SessionAttribute.ALL_USERS_CURRENT_PAGE);
        Optional<String> currentPage = requestContext.getRequestParameter(RequestParameter.NEW_PAGE);
        if (currentPage.isEmpty() && currentTablePage == null) {
            currentTablePage = 1;
        } else if (currentPage.isPresent()) {
            currentTablePage = Integer.parseInt(currentPage.get());
        }
        requestContext.setSessionAttribute(SessionAttribute.ALL_USERS_CURRENT_PAGE, currentTablePage);
        int start = (currentTablePage - 1) * ViewAllUsersTag.LIST_LINES_COUNT;
        int end = ViewAllUsersTag.LIST_LINES_COUNT + start;
        AdminService adminService = AdminServiceImpl.getInstance();
        try {
            List<User> allUsers = adminService.findAll(start, end);
            requestContext.setSessionAttribute(SessionAttribute.ALL_USERS_LIST, allUsers);
            int usersCount = adminService.countUsers();
            requestContext.setSessionAttribute(SessionAttribute.USERS_COUNT, usersCount);
            if(allUsers.size() == 0) {
                requestContext.setRequestAttribute(RequestAttribute.CONFIRM_MESSAGE, FriendlyMessage.EMPTY_USER_LIST);
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
        return new CommandResult(PathJsp.ALL_USERS, TransitionType.FORWARD);
    }
}