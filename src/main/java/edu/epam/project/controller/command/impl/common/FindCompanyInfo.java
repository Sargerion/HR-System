package edu.epam.project.controller.command.impl.common;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Company;
import edu.epam.project.model.service.CompanyService;
import edu.epam.project.model.service.impl.CompanyServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class FindCompanyInfo implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_FIND_COMPANY_PARAMETERS = "Empty find company parameters";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        CompanyService companyService = CompanyServiceImpl.getInstance();
        CommandResult commandResult = new CommandResult(PathJsp.VIEW_COMPANY_PAGE, TransitionType.FORWARD);
        Optional<String> companyId = requestContext.getRequestParameter(RequestParameter.COMPANY_BUTTON);
        if (companyId.isEmpty()) {
            requestContext.setRequestAttribute(RequestAttribute.ERROR_MESSAGE, EMPTY_FIND_COMPANY_PARAMETERS);
        } else {
            Optional<Company> company = Optional.empty();
            Optional<String> errorMessage = Optional.empty();
            Map<Optional<Company>, Optional<String>> findResult;
            try {
                findResult = companyService.findCompanyById(companyId.get());
                for (Map.Entry<Optional<Company>, Optional<String>> entry : findResult.entrySet()) {
                    company = entry.getKey();
                    errorMessage = entry.getValue();
                }
                if (errorMessage.isPresent()) {
                    requestContext.setRequestAttribute(RequestAttribute.ERROR_MESSAGE, errorMessage.get());
                } else {
                    company.ifPresent(value -> requestContext.setSessionAttribute(SessionAttribute.COMPANY, value));
                }
            } catch (ServiceException e) {
                logger.error(e);
                throw new CommandException(e);
            }
        }
        return commandResult;
    }
}