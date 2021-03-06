package edu.epam.project.tag;

import edu.epam.project.controller.command.CommandName;
import edu.epam.project.controller.command.SessionAttribute;
import edu.epam.project.controller.command.SessionRequestContext;
import edu.epam.project.model.entity.User;
import edu.epam.project.tag.util.TagUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import java.util.List;
import java.util.ResourceBundle;
import java.io.IOException;

public class ViewAllUsersTag extends TagSupport {

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
        int usersCount = (int) sessionRequestContext.getSessionAttribute(SessionAttribute.USERS_COUNT);
        int pagesCount = usersCount % LIST_LINES_COUNT == 0 ? usersCount / LIST_LINES_COUNT : usersCount / LIST_LINES_COUNT + 1;
        String command = CommandName.USER_LIST.name();
        TagUtil.paginate(pageContext, pagesCount, command);
        return SKIP_BODY;
    }

    private void createList(JspWriter writer, SessionRequestContext sessionRequestContext) throws JspException {
        try {
            writer.write("<table id=\"customers\">");
            createTableHeader(writer, sessionRequestContext);
            writer.write("<tbody>");
            List<User> allUsers = (List<User>) sessionRequestContext.getSessionAttribute(SessionAttribute.ALL_USERS_LIST);
            createLines(writer, sessionRequestContext, allUsers);
            writer.write("</tbody></table>");
        } catch (IOException e) {
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
            throw new JspException(e);
        }
    }

    private void createLines(JspWriter writer, SessionRequestContext sessionRequestContext, List<User> allUsers) throws JspException {
        try {
            if (allUsers != null) {
                int size = allUsers.size();
                int currentPage = (int) sessionRequestContext.getSessionAttribute(SessionAttribute.ALL_USERS_CURRENT_PAGE);
                for (int i = 0; i < LIST_LINES_COUNT; i++) {
                    int lineNumber = LIST_LINES_COUNT * (currentPage - 1) + i + 1;
                    if (size > i) {
                        User user = allUsers.get(i);
                        writer.write("<tr><th>" + lineNumber + "</th>");
                        writer.write("<td>" + user.getEntityId() + "</td>");
                        writer.write("<td>" + user.getLogin() + "</td>");
                        writer.write("<td>" + user.getEmail() + "</td>");
                        writer.write("<td>" + user.getType() + "</td>");
                        writer.write("<td>" + user.getStatus() + "</td>");
                    } else {
                        writer.write("<tr><th>" + lineNumber + "</th>");
                        writer.write("<td></td><td></td><td></td><td></td><td></td>");
                    }
                    writer.write("</tr>");
                }
            }
        } catch (IOException e) {
            throw new JspException(e);
        }
    }
}