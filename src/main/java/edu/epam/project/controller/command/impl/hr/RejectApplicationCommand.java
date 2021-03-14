package edu.epam.project.controller.command.impl.hr;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.MailSendException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.service.ApplicationService;
import edu.epam.project.model.service.CompanyService;
import edu.epam.project.model.service.UserService;
import edu.epam.project.model.service.impl.ApplicationServiceImpl;
import edu.epam.project.model.service.impl.CompanyServiceImpl;
import edu.epam.project.model.service.impl.UserServiceImpl;
import edu.epam.project.model.util.mail.MailSender;
import edu.epam.project.model.util.message.FriendlyMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

/**
 * The implementation of Command interface for application rejecting by company_hr user type.
 * @author Sargerion.
 */
public class RejectApplicationCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_REJECT_APPLICATION_PARAMETERS = "Empty reject application parameters";
    private static final String FINDER_NOT_HIRE_STATUS = "Not Hire";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        ApplicationService applicationService = ApplicationServiceImpl.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        CompanyService companyService = CompanyServiceImpl.getInstance();
        User hr = (User) requestContext.getSessionAttribute(SessionAttribute.USER);
        Optional<String> applicationId = requestContext.getRequestParameter(RequestParameter.REJECT_APPLICATION_BUTTON);
        CommandResult commandResult = new CommandResult(PathJsp.APPLICATIONS_VIEW_PAGE, TransitionType.REDIRECT);
        if (applicationId.isEmpty()) {
            requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, EMPTY_REJECT_APPLICATION_PARAMETERS);
        } else {
            Integer finderId = 0;
            Optional<String> errorMessage = Optional.empty();
            Map<Integer, Optional<String>> rejectResult;
            try {
                rejectResult = applicationService.rejectApplication(applicationId.get(), FINDER_NOT_HIRE_STATUS);
                for (Map.Entry<Integer, Optional<String>> entry : rejectResult.entrySet()) {
                    finderId = entry.getKey();
                    errorMessage = entry.getValue();
                }
                if (errorMessage.isPresent()) {
                    requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, errorMessage.get());
                } else {
                    Optional<User> user = userService.findById(finderId);
                    String companyName = companyService.findCompanyNameByHrLogin(hr.getLogin()).get();
                    MailSender mailSender = MailSender.getInstance();
                    mailSender.sendNoHireNotificationFinder(user.get(), companyName);
                    requestContext.setSessionAttribute(SessionAttribute.CONFIRM_MESSAGE, FriendlyMessage.REJECT_FINDER_APPLICATION);
                }
            } catch (ServiceException | MailSendException e) {
                logger.error(e);
                throw new CommandException(e);
            }
        }
        return commandResult;
    }
}