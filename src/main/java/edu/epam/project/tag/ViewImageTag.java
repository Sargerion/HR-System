package edu.epam.project.tag;

import edu.epam.project.controller.command.SessionAttribute;
import edu.epam.project.controller.command.SessionRequestContext;
import edu.epam.project.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Custom tag for viewing avatar by authorized users.
 * @author Sargerion.
 */
public class ViewImageTag extends TagSupport {

    private static final String UPLOAD_AVATAR_DIRECTORY_FOR_VIEW = "user_avatars";
    private static final String DEFAULT_AVATAR = "default_avatar.png";

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        SessionRequestContext sessionRequestContext = new SessionRequestContext(request);
        User user = (User) sessionRequestContext.getSessionAttribute(SessionAttribute.USER);
        String imageName = user.getAvatarName();
        try {
            if (imageName == null || imageName.isEmpty()) {
                TagExpander.showImage(pageContext, UPLOAD_AVATAR_DIRECTORY_FOR_VIEW + "/" + DEFAULT_AVATAR);
            } else {
                imageName = imageName.substring(imageName.lastIndexOf(UPLOAD_AVATAR_DIRECTORY_FOR_VIEW));
                TagExpander.showImage(pageContext, imageName);
            }
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}