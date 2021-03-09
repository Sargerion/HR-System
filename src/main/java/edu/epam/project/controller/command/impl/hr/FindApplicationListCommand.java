package edu.epam.project.controller.command.impl.hr;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Application;
import edu.epam.project.model.service.ApplicationService;
import edu.epam.project.model.service.impl.ApplicationServiceImpl;
import edu.epam.project.model.util.message.FriendlyMessage;
import edu.epam.project.tag.ViewAllApplicationsTag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class FindApplicationListCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        Integer currentTablePage = (Integer) requestContext.getSessionAttribute(SessionAttribute.APPLICATION_LIST_CURRENT_PAGE);
        Optional<String> nextPage = requestContext.getRequestParameter(RequestParameter.NEW_PAGE);
        if (nextPage.isEmpty() && currentTablePage == null) {
            currentTablePage = 1;
        } else if (nextPage.isPresent()) {
            currentTablePage = Integer.parseInt(nextPage.get());
        }
        requestContext.setSessionAttribute(SessionAttribute.APPLICATION_LIST_CURRENT_PAGE, currentTablePage);
        int start = (currentTablePage - 1) * ViewAllApplicationsTag.LIST_LINES_COUNT;
        int end = ViewAllApplicationsTag.LIST_LINES_COUNT + start;
        ApplicationService applicationService = ApplicationServiceImpl.getInstance();
        try {
            List<Application> applications = applicationService.findAll(start, end);
            requestContext.setSessionAttribute(SessionAttribute.APPLICATION_LIST, applications);
            int applicationsCount = applicationService.countApplications();
            requestContext.setSessionAttribute(SessionAttribute.APPLICATIONS_COUNT, applicationsCount);
            if (applications.size() == 0) {
                requestContext.setRequestAttribute(RequestAttribute.CONFIRM_MESSAGE, FriendlyMessage.EMPTY_APPLICATION_LIST);
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
        return new CommandResult(PathJsp.APPLICATIONS_VIEW_PAGE, TransitionType.FORWARD);
    }
}