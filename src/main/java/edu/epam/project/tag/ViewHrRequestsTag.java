package edu.epam.project.tag;

import edu.epam.project.controller.command.CommandName;
import edu.epam.project.controller.command.SessionAttribute;
import edu.epam.project.controller.command.SessionRequestContext;
import edu.epam.project.model.entity.User;
import edu.epam.project.tag.util.TagUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewHrRequestsTag extends TagSupport {

    private static final Logger logger = LogManager.getLogger();
    public static final int LIST_LINES_COUNT = 2;
    private static final String USER_ID_BUNDLE = "all_users_table_id";
    private static final String USER_LOGIN_BUNDLE = "all_users_table_login";
    private static final String USER_EMAIL_BUNDLE = "all_users_table_email";
    private static final String USER_TYPE_BUNDLE = "all_users_table_type";
    private static final String USER_STATUS_BUNDLE = "all_users_table_status";

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        JspWriter writer = pageContext.getOut();
        SessionRequestContext sessionRequestContext = new SessionRequestContext(request);
        createList(writer, sessionRequestContext);
        int hrsCount = (int) sessionRequestContext.getSessionAttribute(SessionAttribute.NOT_ACTIVE_HRs_COUNT);
        int pagesCount = hrsCount % LIST_LINES_COUNT == 0 ? hrsCount / LIST_LINES_COUNT : hrsCount / LIST_LINES_COUNT + 1;
        String command = CommandName.NOT_ACTIVE_HR_LIST.toString().toLowerCase();
        TagUtil.paginate(pageContext, pagesCount, command);
        return SKIP_BODY;
    }

    private void createList(JspWriter writer, SessionRequestContext sessionRequestContext) throws JspException {
        try {
            writer.write("<table id=\"customers\">");
            createTableHeader(writer, sessionRequestContext);
            writer.write("<tbody>");
            List<User> notActiveHRs = (List<User>) sessionRequestContext.getSessionAttribute(SessionAttribute.HR_LIST);
            createLines(writer, sessionRequestContext, notActiveHRs);
            writer.write("</tbody></table>");
        } catch (IOException e) {
            logger.error(e);
            throw new JspException(e);
        }
    }

    private void createTableHeader(JspWriter writer, SessionRequestContext sessionRequestContext) throws JspException {
        try {
            String locale = sessionRequestContext.getLocale();
            ResourceBundle resourceBundle = TagUtil.getLocalizeText(locale);
            String userID = resourceBundle.getString(USER_ID_BUNDLE);
            String userLogin = resourceBundle.getString(USER_LOGIN_BUNDLE);
            String userEmail = resourceBundle.getString(USER_EMAIL_BUNDLE);
            String userType = resourceBundle.getString(USER_TYPE_BUNDLE);
            String userStatus = resourceBundle.getString(USER_STATUS_BUNDLE);
            writer.write("<thead><tr>");
            writer.write("<th><span style=\"font-weight: bold\">№</span></th>");
            TagUtil.createTableHeadItem(writer, userID);
            TagUtil.createTableHeadItem(writer, userLogin);
            TagUtil.createTableHeadItem(writer, userEmail);
            TagUtil.createTableHeadItem(writer, userType);
            TagUtil.createTableHeadItem(writer, userStatus);
            writer.write("</tr></thead>");
        } catch (IOException e) {
            logger.error(e);
            throw new JspException(e);
        }
    }

    private void createLines(JspWriter writer, SessionRequestContext sessionRequestContext, List<User> notActiveHRs) throws JspException {
        try {
            if (notActiveHRs != null) {
                int size = notActiveHRs.size();
                int currentPage = (int) sessionRequestContext.getSessionAttribute(SessionAttribute.HR_LIST_CURRENT_PAGE);
                for (int i = 0; i < LIST_LINES_COUNT; i++) {
                    int lineNumber = LIST_LINES_COUNT * (currentPage - 1) + i + 1;
                    if (size > i) {
                        User user = notActiveHRs.get(i);
                        writer.write("<tr><th>" + lineNumber + "</th>");
                        writer.write("<td>" + user.getEntityId() + "</td>");
                        writer.write("<td>" + user.getLogin() + "</td>");
                        writer.write("<td>" + user.getEmail() + "</td>");
                        writer.write("<td>" + user.getType() + "</td>");
                        writer.write("<td>" + user.getStatus() + "</td>");
                    } else {
                        writer.write("<tr><th>" + lineNumber + "</th>");
                        writer.write("<td></td><td></td><td></td><td></td><td></td><td></td>");
                    }
                    writer.write("</tr>");
                }
            } else {
                logger.error("HR list -> null");
            }
        } catch (IOException e) {
            logger.error(e);
            throw new JspException(e);
        }
    }
}