package edu.epam.project.controller;

import edu.epam.project.controller.command.SessionAttribute;
import edu.epam.project.controller.command.SessionRequestContext;
import edu.epam.project.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet(urlPatterns = {"/user_avatars/*"})
public class ImageViewServlet extends HttpServlet {

    private static final String UPLOAD_AVATAR_DIRECTORY = "D:/project_directory/user_avatars";
    private static final String DEFAULT_AVATAR = "default_avatar.png";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionRequestContext sessionRequestContext = new SessionRequestContext(request);
        User user = (User) sessionRequestContext.getSessionAttribute(SessionAttribute.USER);
        String imageName = user.getAvatarName();
        if (imageName == null || imageName.isEmpty()) {
            imageName = UPLOAD_AVATAR_DIRECTORY + "/" + DEFAULT_AVATAR;
        }
        byte[] image = Files.readAllBytes(Paths.get(imageName));
        response.setHeader("Content-Type", getServletContext().getMimeType(imageName));
        response.setHeader("Content-Length", String.valueOf(image.length));
        response.setHeader("Content-Disposition", "inline filename=\"" + imageName + "\"");
        response.setContentType(getServletContext().getMimeType(imageName));
        response.setContentLength(image.length);
        response.getOutputStream().write(image);
    }
}