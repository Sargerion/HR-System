package edu.epam.project.controller.command.impl.admin;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;

import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Company;
import edu.epam.project.model.service.AdminService;
import edu.epam.project.model.service.impl.AdminServiceImpl;

import edu.epam.project.model.util.message.ErrorMessage;
import edu.epam.project.model.util.message.FriendlyMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class AddCompanyCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_ADD_COMPANY_PARAMETERS = "Empty add company parameters";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        AdminService adminService = AdminServiceImpl.getInstance();
        Optional<String> companyName = requestContext.getRequestParameter(RequestParameter.COMPANY_NAME);
        Optional<String> companyOwner = requestContext.getRequestParameter(RequestParameter.COMPANY_OWNER);
        Optional<String> companyAddress = requestContext.getRequestParameter(RequestParameter.COMPANY_TOWN);
        Optional<String> companyVacancyId = requestContext.getRequestParameter(RequestParameter.VACANCY);
        Optional<String> companyHrLogin = requestContext.getRequestParameter(RequestParameter.COMPANY_HR_LOGIN);
        CommandResult commandResult = new CommandResult(PathJsp.ADD_COMPANY_PAGE, TransitionType.REDIRECT);
        if (companyName.isEmpty() || companyOwner.isEmpty() || companyAddress.isEmpty() || companyVacancyId.isEmpty() || companyHrLogin.isEmpty()) {
            requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, EMPTY_ADD_COMPANY_PARAMETERS);
        } else {
            Optional<Company> company = Optional.empty();
            List<String> errorMessages = new ArrayList<>();
            Map<String, String> correctFields = new HashMap<>();
            Map<Optional<Company>, Map<List<String>, Map<String, String>>> addResult;
            try {
                addResult = adminService.addCompany(companyName.get(), companyOwner.get(), companyAddress.get(), companyVacancyId.get(), companyHrLogin.get());
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
                    requestContext.setSessionAttribute(SessionAttribute.CORRECT_VACANCY_ID, correctFields.get(SessionAttribute.CORRECT_VACANCY_ID));
                    requestContext.setSessionAttribute(SessionAttribute.CORRECT_VACANCY_NAME, correctFields.get(SessionAttribute.CORRECT_VACANCY_NAME));
                    requestContext.setSessionAttribute(SessionAttribute.CORRECT_COMPANY_HR_LOGIN, correctFields.get(SessionAttribute.CORRECT_COMPANY_HR_LOGIN));
                    for (int i = 0; i < errorMessages.size(); ) {
                        if (errorMessages.contains(ErrorMessage.COMPANY_NAME_DUPLICATE)) {
                            requestContext.setSessionAttribute(SessionAttribute.ERROR_DUPLICATE_COMPANY_NAME, errorMessages.get(i));
                            i++;
                        }
                        if (errorMessages.contains(ErrorMessage.COMPANY_HR_LOGIN_DUPLICATE)) {
                            requestContext.setSessionAttribute(SessionAttribute.ERROR_DUPLICATE_COMPANY_HR_LOGIN, errorMessages.get(i));
                            i++;
                        }
                    }
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