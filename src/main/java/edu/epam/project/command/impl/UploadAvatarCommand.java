package edu.epam.project.command.impl;

import edu.epam.project.command.*;
import edu.epam.project.entity.User;
import edu.epam.project.entity.UserType;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ExceptionMessage;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.service.UserService;
import edu.epam.project.service.impl.UserServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.*;
import java.util.*;

public class UploadAvatarCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String UPLOAD_AVATAR_DIRECTORY = "D:/project_directory/user_avatars";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        User user = (User) requestContext.getSessionAttribute(SessionAttribute.USER);
        CommandResult commandResult = null;
        List<Part> fileParts = requestContext.getFileParts();
        String fileName = null;
        try {
            for (Part part : fileParts) {
                fileName = part.getSubmittedFileName();
                if (fileName != null) {
                    part.write(UPLOAD_AVATAR_DIRECTORY + File.separator + fileName);
                }
            }

        } catch (IOException e) {
            logger.error(e);
        }
        try {
            if (!fileName.isEmpty()) {
                user.setAvatarName(UPLOAD_AVATAR_DIRECTORY + "/" + fileName);
                userService.updateAvatar(user);
                UserType userType = user.getType();
                switch (userType) {
                    case ADMIN -> {
                        commandResult = new CommandResult(PathJsp.ADMIN_PAGE, TransitionType.FORWARD);
                        logger.info("Admin with login -> {} change avatar", user.getLogin());
                    }
                    case COMPANY_HR -> {
                        commandResult = new CommandResult(PathJsp.HR_PAGE, TransitionType.FORWARD);
                        logger.info("Company HR with login -> {} change avatar", user.getLogin());
                    }
                    case FINDER -> {
                        commandResult = new CommandResult(PathJsp.FINDER_PAGE, TransitionType.FORWARD);
                        logger.info("Finder with login -> {} change avatar", user.getLogin());
                    }
                }
            } else {
                requestContext.setRequestAttribute(RequestAttribute.ERROR_MESSAGE, ExceptionMessage.ERROR_WITH_UPLOAD);
                commandResult = new CommandResult(PathJsp.CHANGE_AVATAR_PAGE, TransitionType.FORWARD);
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
        return commandResult;
    }
}