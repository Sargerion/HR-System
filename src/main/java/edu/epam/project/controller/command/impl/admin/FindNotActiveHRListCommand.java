package edu.epam.project.controller.command.impl.admin;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.service.AdminService;
import edu.epam.project.model.service.impl.AdminServiceImpl;
import edu.epam.project.model.util.message.FriendlyMessage;
import edu.epam.project.tag.ViewHrRequestsTag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class FindNotActiveHRListCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        Integer currentTablePage = (Integer) requestContext.getSessionAttribute(SessionAttribute.HR_LIST_CURRENT_PAGE);
        Optional<String> currentPage = requestContext.getRequestParameter(RequestParameter.NEW_PAGE);
        if (currentPage.isEmpty() && currentTablePage == null) {
            currentTablePage = 1;
        } else if (currentPage.isPresent()) {
            currentTablePage = Integer.parseInt(currentPage.get());
        }
        requestContext.setSessionAttribute(SessionAttribute.HR_LIST_CURRENT_PAGE, currentTablePage);
        int start = (currentTablePage - 1) * ViewHrRequestsTag.LIST_LINES_COUNT;
        int end = ViewHrRequestsTag.LIST_LINES_COUNT + start;
        AdminService adminService = AdminServiceImpl.getInstance();
        try {
            List<User> notActiveHRs = adminService.findNotActiveHRList(start, end);
            requestContext.setSessionAttribute(SessionAttribute.HR_LIST, notActiveHRs);
            int notActiveHRsCount = adminService.countNotActiveHRs();
            requestContext.setSessionAttribute(SessionAttribute.NOT_ACTIVE_HRs_COUNT, notActiveHRsCount);
            if (notActiveHRs.size() == 0) {
                requestContext.setRequestAttribute(RequestAttribute.CONFIRM_MESSAGE, FriendlyMessage.EMPTY_HR_LIST);
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
        return new CommandResult(PathJsp.ADMIN_HR_VIEW_PAGE, TransitionType.FORWARD);
    }
}