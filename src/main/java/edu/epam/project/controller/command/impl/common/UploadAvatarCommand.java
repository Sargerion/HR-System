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

/**
 * The implementation of Command interface for avatar uploading by common authorized user types.
 * @author Sargerion.
 */
public class UploadAvatarCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String UPLOAD_AVATAR_DIRECTORY = "D:/project_directory/user_avatars";
    private static final char FILE_FORMAT_SEPARATOR = '.';
    private static final String EMPTY_UPLOAD_FILE_PARAMETERS = "Empty upload parameters";

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        User user = (User) requestContext.getSessionAttribute(SessionAttribute.USER);
        UserType userType = user.getType();
        CommandResult commandResult;
        List<Part> fileParts = requestContext.getFileParts();
        String fileName = null;
        try {
            for (Part part : fileParts) {
                fileName = part.getSubmittedFileName();
                if (fileName != null && !fileName.isEmpty()) {
                    fileName = buildNewFileName(fileName);
                    part.write(fileName);
                }
            }
        } catch (IOException e) {
            logger.error(e);
        }
        if (fileName == null) {
            commandResult = defineCommandResult(userType);
            requestContext.setRequestAttribute(RequestAttribute.ERROR_MESSAGE, EMPTY_UPLOAD_FILE_PARAMETERS);
        } else {
            try {
                if (!fileName.isEmpty()) {
                    user.setAvatarName(fileName);
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

    @Override
    public CommandResult defineCommandResult(UserType userType) {
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

    private String buildNewFileName(String oldFileName) {
        String fileFormat = oldFileName.substring(oldFileName.indexOf(FILE_FORMAT_SEPARATOR));
        return UPLOAD_AVATAR_DIRECTORY + "/" + UUID.randomUUID().toString() + fileFormat;
    }
}