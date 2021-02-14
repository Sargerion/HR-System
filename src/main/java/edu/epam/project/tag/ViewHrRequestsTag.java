package edu.epam.project.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ViewHrRequestsTag extends TagSupport {

    private static final Logger logger = LogManager.getLogger();
    private static final int LIST_HEIGHT = 5;

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        JspWriter writer = pageContext.getOut();

        return SKIP_BODY;
    }



    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    private void createList(JspWriter writer, HttpSession session) throws JspException {
        try {
            writer.write("<table>");

            writer.write("</table>");
        } catch (IOException e) {
            logger.error(e);
            throw new JspException(e);
        }
    }
}