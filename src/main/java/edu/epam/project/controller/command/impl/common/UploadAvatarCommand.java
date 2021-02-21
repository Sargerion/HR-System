package edu.epam.project.controller.command.impl.common;

import edu.epam.project.controller.command.*;
import edu.epam.project.model.entity.User;
import edu.epam.project.exception.CommandException;
import edu.epam.project.model.entity.UserType;
import edu.epam.project.model.util.message.ErrorMessage;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.service.UserService;
import edu.epam.project.model.service.impl.UserServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.*;
import java.util.*;

public class UploadAvatarCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String UPLOAD_AVATAR_DIRECTORY = "D:/project_directory/user_avatars";
    private static final String UPLOAD_AVATAR_DIRECTORY_FOR_VIEW = "user_avatars";
    private static final String EMPTY_UPLOAD_FILE_PARAMETERS = "Empty upload parameters";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        User user = (User) requestContext.getSessionAttribute(SessionAttribute.USER);
        UserType userType = UserType.GUEST;
        CommandResult commandResult;
        List<Part> fileParts = requestContext.getFileParts();
        String fileName = null;
        if (user != null) {
            userType = user.getType();
        }
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
        if (fileName == null) {
            commandResult = defineWithoutGuestCommandResult(userType);
            requestContext.setRequestAttribute(RequestAttribute.ERROR_MESSAGE, EMPTY_UPLOAD_FILE_PARAMETERS);
        } else {
            try {
                if (!fileName.isEmpty()) {
                    requestContext.setRequestAttribute(RequestAttribute.VIEW_IMAGE, UPLOAD_AVATAR_DIRECTORY.
                            substring(UPLOAD_AVATAR_DIRECTORY.lastIndexOf(UPLOAD_AVATAR_DIRECTORY_FOR_VIEW)));
                    user.setAvatarName(UPLOAD_AVATAR_DIRECTORY + "/" + fileName);
                    userService.updateAvatar(user);
                    commandResult = new CommandResult(PathJsp.CHANGE_AVATAR_PAGE, TransitionType.FORWARD);
                    logger.info("User -> {}, change avatar", user);
                } else {
                    requestContext.setRequestAttribute(RequestAttribute.ERROR_MESSAGE, ErrorMessage.ERROR_WITH_UPLOAD);
                    commandResult = new CommandResult(PathJsp.CHANGE_AVATAR_PAGE, TransitionType.FORWARD);
                }
            } catch (ServiceException e) {
                logger.error(e);
                throw new CommandException(e);
            }
        }
        return commandResult;
    }

    private CommandResult defineWithoutGuestCommandResult(UserType userType) {
        CommandResult commandResult = null;
        switch (userType) {
            case ADMIN -> {
                commandResult = new CommandResult(PathJsp.ADMIN_PAGE, TransitionType.FORWARD);
            }
            case COMPANY_HR -> {
                commandResult = new CommandResult(PathJsp.HR_PAGE, TransitionType.FORWARD);
            }
            case FINDER -> {
                commandResult = new CommandResult(PathJsp.FINDER_PAGE, TransitionType.FORWARD);
            }
        }
        return commandResult;
    }
}