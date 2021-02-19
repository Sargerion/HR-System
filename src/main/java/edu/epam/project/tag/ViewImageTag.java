package edu.epam.project.tag;

import edu.epam.project.command.SessionAttribute;
import edu.epam.project.command.SessionRequestContext;
import edu.epam.project.entity.User;
import edu.epam.project.tag.util.TagUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

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
                TagUtil.showImage(pageContext, UPLOAD_AVATAR_DIRECTORY_FOR_VIEW + "/" + DEFAULT_AVATAR);
            } else {
                imageName = imageName.substring(imageName.lastIndexOf(UPLOAD_AVATAR_DIRECTORY_FOR_VIEW));
                TagUtil.showImage(pageContext, imageName);
            }
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}