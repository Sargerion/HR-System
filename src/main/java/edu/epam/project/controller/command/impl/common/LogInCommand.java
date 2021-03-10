package edu.epam.project.controller.command.impl.common;

import edu.epam.project.controller.command.*;
import edu.epam.project.model.entity.Finder;
import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.UserType;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.service.CompanyService;
import edu.epam.project.model.service.FinderService;
import edu.epam.project.model.service.SpecialtyService;
import edu.epam.project.model.service.UserService;
import edu.epam.project.model.service.impl.CompanyServiceImpl;
import edu.epam.project.model.service.impl.FinderServiceImpl;
import edu.epam.project.model.service.impl.SpecialtyServiceImpl;
import edu.epam.project.model.service.impl.UserServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class LogInCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_LOGIN_PARAMETERS = "Empty login parameters";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        SpecialtyService specialtyService = SpecialtyServiceImpl.getInstance();
        CompanyService companyService = CompanyServiceImpl.getInstance();
        FinderService finderService = FinderServiceImpl.getInstance();
        Optional<String> login = requestContext.getRequestParameter(RequestParameter.LOGIN);
        Optional<String> password = requestContext.getRequestParameter(RequestParameter.PASSWORD);
        Optional<String> userAvatar;
        CommandResult commandResult = null;
        if (login.isEmpty() || password.isEmpty()) {
            requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, EMPTY_LOGIN_PARAMETERS);
            commandResult = new CommandResult(PathJsp.LOGIN_PAGE, TransitionType.REDIRECT);
        } else {
            User alreadyLoggedUser = (User) requestContext.getSessionAttribute(SessionAttribute.USER);
            Optional<User> optionalUser = Optional.empty();
            Optional<String> optionalErrorMessage = Optional.empty();
            Optional<String> correctLogin = Optional.empty();
            Map<Optional<User>, Map<Optional<String>, Optional<String>>> loginResult;
            try {
                if (alreadyLoggedUser == null) {
                    loginResult = userService.loginUser(login.get(), password.get());
                    for (Map.Entry<Optional<User>, Map<Optional<String>, Optional<String>>> entry : loginResult.entrySet()) {
                        optionalUser = entry.getKey();
                        for (Map.Entry<Optional<String>, Optional<String>> entryMessages : entry.getValue().entrySet()) {
                            optionalErrorMessage = entryMessages.getKey();
                            correctLogin = entryMessages.getValue();
                        }
                    }
                    correctLogin.ifPresent(s -> requestContext.setSessionAttribute(SessionAttribute.CORRECT_LOGIN, s));
                    if (optionalErrorMessage.isPresent()) {
                        requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, optionalErrorMessage.get());
                        commandResult = new CommandResult(PathJsp.LOGIN_PAGE, TransitionType.REDIRECT);
                    } else {
                        if (optionalUser.isPresent()) {
                            User user = optionalUser.get();
                            userAvatar = userService.findUserAvatar(user);
                            userAvatar.ifPresent(user::setAvatarName);
                            requestContext.setSessionAttribute(SessionAttribute.USER, user);
                            List<Specialty> specialties = specialtyService.findAllSpecialties();
                            requestContext.setSessionAttribute(SessionAttribute.SPECIALTY_LIST, specialties);
                            commandResult = defineIfAlreadyLoggedCommandResult(user, requestContext, companyService, finderService);
                        }
                    }
                } else {
                    commandResult = defineIfAlreadyLoggedCommandResult(alreadyLoggedUser, requestContext, companyService, finderService);
                }
            } catch (ServiceException e) {
                logger.error(e);
                throw new CommandException(e);
            }
        }
        return commandResult;
    }

    private CommandResult defineIfAlreadyLoggedCommandResult(User user, SessionRequestContext requestContext,
                                                             CompanyService companyService, FinderService finderService) throws ServiceException {
        CommandResult commandResult = null;
        UserType userType = user.getType();
        switch (userType) {
            case ADMIN -> {
                commandResult = new CommandResult(PathJsp.ADMIN_PAGE, TransitionType.REDIRECT);
                logger.info("Admin with login -> {} entered", user.getLogin());
            }
            case COMPANY_HR -> {
                String hrCompany = companyService.findCompanyNameByHrLogin(user.getLogin()).get();
                requestContext.setSessionAttribute(SessionAttribute.HR_COMPANY, hrCompany);
                commandResult = new CommandResult(PathJsp.HR_PAGE, TransitionType.REDIRECT);
                logger.info("Company HR with login -> {} entered", user.getLogin());
            }
            case FINDER -> {
                Optional<Finder> finder = finderService.findById(user.getEntityId());
                finder.ifPresent(value -> requestContext.setSessionAttribute(SessionAttribute.FINDER, value));
                commandResult = new CommandResult(PathJsp.FINDER_PAGE, TransitionType.REDIRECT);
                logger.info("Finder with login -> {} entered", user.getLogin());
            }
        }
        return commandResult;
    }
}