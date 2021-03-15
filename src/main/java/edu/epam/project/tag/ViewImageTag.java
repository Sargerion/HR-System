package edu.epam.project.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Custom tag for viewing avatar by authorized users.
 * @author Sargerion.
 */
public class ViewImageTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        try {
            TagExpander.showImage(pageContext);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}