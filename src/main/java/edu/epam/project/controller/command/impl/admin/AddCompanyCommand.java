package edu.epam.project.controller.command.impl.admin;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Company;
import edu.epam.project.model.service.CompanyService;
import edu.epam.project.model.service.impl.CompanyServiceImpl;
import edu.epam.project.model.util.message.FriendlyMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * The implementation of Command interface for adding a company by admin user type.
 * @author Sargerion.
 */
public class AddCompanyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_ADD_COMPANY_PARAMETERS = "Empty add company parameters";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        CompanyService companyService = CompanyServiceImpl.getInstance();
        Optional<String> companyName = requestContext.getRequestParameter(RequestParameter.COMPANY_NAME);
        Optional<String> companyOwner = requestContext.getRequestParameter(RequestParameter.COMPANY_OWNER);
        Optional<String> companyAddress = requestContext.getRequestParameter(RequestParameter.COMPANY_TOWN);
        Optional<String> companyHrLogin = requestContext.getRequestParameter(RequestParameter.COMPANY_HR_LOGIN);
        CommandResult commandResult = new CommandResult(PathJsp.ADD_COMPANY_PAGE, TransitionType.REDIRECT);
        if (companyName.isEmpty() || companyOwner.isEmpty() || companyAddress.isEmpty() || companyHrLogin.isEmpty()) {
            requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, EMPTY_ADD_COMPANY_PARAMETERS);
        } else {
            Optional<Company> company = Optional.empty();
            List<String> errorMessages = new ArrayList<>();
            Map<String, String> correctFields = new HashMap<>();
            Map<Optional<Company>, Map<List<String>, Map<String, String>>> addResult;
            try {
                addResult = companyService.addCompany(companyName.get(), companyOwner.get(), companyAddress.get(), companyHrLogin.get());
                for (Map.Entry<Optional<Company>, Map<List<String>, Map<String, String>>> entry : addResult.entrySet()) {
                    company = entry.getKey();
                    for (Map.Entry<List<String>, Map<String, String>> listMapEntry : entry.getValue().entrySet()) {
                        errorMessages = listMapEntry.getKey();
                        correctFields = listMapEntry.getValue();
                    }
                }
                if (!errorMessages.isEmpty()) {
                    requestContext.setSessionAttribute(SessionAttribute.CORRECT_COMPANY_NAME, correctFields.get(SessionAttribute.CORRECT_COMPANY_NAME));
                    requestContext.setSessionAttribute(SessionAttribute.CORRECT_COMPANY_OWNER, correctFields.get(SessionAttribute.CORRECT_COMPANY_OWNER));
                    requestContext.setSessionAttribute(SessionAttribute.CORRECT_COMPANY_TOWN, correctFields.get(SessionAttribute.CORRECT_COMPANY_TOWN));
                    requestContext.setSessionAttribute(SessionAttribute.CORRECT_COMPANY_HR_LOGIN, correctFields.get(SessionAttribute.CORRECT_COMPANY_HR_LOGIN));
                    requestContext.setSessionAttribute(SessionAttribute.ERROR_ADD_COMPANY_LIST, errorMessages);
                } else {
                    if (company.isPresent()) {
                        requestContext.setSessionAttribute(SessionAttribute.CONFIRM_MESSAGE, FriendlyMessage.ADD_COMPANY);
                    }
                }
            } catch (ServiceException e) {
                logger.error(e);
                throw new CommandException(e);
            }
        }
        return commandResult;
    }
}