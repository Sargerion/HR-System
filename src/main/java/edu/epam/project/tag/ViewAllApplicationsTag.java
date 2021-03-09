package edu.epam.project.tag;

import edu.epam.project.controller.command.CommandName;
import edu.epam.project.controller.command.SessionAttribute;
import edu.epam.project.controller.command.SessionRequestContext;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Application;
import edu.epam.project.model.service.FinderService;
import edu.epam.project.model.service.impl.FinderServiceImpl;
import edu.epam.project.tag.util.TagUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewAllApplicationsTag extends TagSupport {

    public static final int LIST_LINES_COUNT = 2;
    private static final String APPLICATION_ID_BUNDLE = "all_applications_table_id";
    private static final String APPLICATION_VACANCY_NAME_BUNDLE = "all_applications_table_vacancy_name";
    private static final String APPLICATION_FINDER_LOGIN = "all_applications_table_finder_login";
    private static final String APPLICATION_OPERATION = "all_applications_table_operation";
    private static final String APPLICATION_OPERATION_CONFIRM = "all_applications_table_operation_confirm";
    private static final String APPLICATION_OPERATION_REJECT = "all_applications_table_operation_reject";

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        JspWriter writer = pageContext.getOut();
        SessionRequestContext sessionRequestContext = new SessionRequestContext(request);
        createList(writer, sessionRequestContext);
        int applicationsCount = (int) sessionRequestContext.getSessionAttribute(SessionAttribute.APPLICATIONS_COUNT);
        int pagesCount = applicationsCount % LIST_LINES_COUNT == 0 ? applicationsCount / LIST_LINES_COUNT : applicationsCount / LIST_LINES_COUNT + 1;
        String command = CommandName.FIND_APPLICATION_LIST.name();
        TagUtil.paginate(pageContext, pagesCount, command);
        return SKIP_BODY;
    }

    private void createList(JspWriter writer, SessionRequestContext sessionRequestContext) throws JspException {
        try {
            writer.write("<table id=\"customers\">");
            createTableHeader(writer, sessionRequestContext);
            writer.write("<tbody>");
            List<Application> applications = (List<Application>) sessionRequestContext.getSessionAttribute(SessionAttribute.APPLICATION_LIST);
            createLines(writer, sessionRequestContext, applications);
            writer.write("</tbody></table>");
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    private void createTableHeader(JspWriter writer, SessionRequestContext sessionRequestContext) throws JspException {
        try {
            String locale = sessionRequestContext.getLocale();
            ResourceBundle resourceBundle = TagUtil.getLocalizeText(locale);
            String applicationID = resourceBundle.getString(APPLICATION_ID_BUNDLE);
            String applicationVacancyName = resourceBundle.getString(APPLICATION_VACANCY_NAME_BUNDLE);
            String applicationFinderLogin = resourceBundle.getString(APPLICATION_FINDER_LOGIN);
            String applicationOperation = resourceBundle.getString(APPLICATION_OPERATION);
            writer.write("<thead><tr>");
            writer.write("<th><span style=\"font-weight: bold\">№</span></th>");
            TagUtil.createTableHeadItem(writer, applicationID);
            TagUtil.createTableHeadItem(writer, applicationVacancyName);
            TagUtil.createTableHeadItem(writer, applicationFinderLogin);
            TagUtil.createTableHeadItem(writer, applicationOperation);
            writer.write("</tr></thead>");
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    private void createLines(JspWriter writer, SessionRequestContext sessionRequestContext, List<Application> applications) throws JspException {
        String locale = sessionRequestContext.getLocale();
        ResourceBundle resourceBundle = TagUtil.getLocalizeText(locale);
        FinderService finderService = FinderServiceImpl.getInstance();
        try {
            if (applications != null) {
                int size = applications.size();
                int currentPage = (int) sessionRequestContext.getSessionAttribute(SessionAttribute.APPLICATION_LIST_CURRENT_PAGE);
                Optional<String> finderLogin;
                for (int i = 0; i < LIST_LINES_COUNT; i++) {
                    int lineNumber = LIST_LINES_COUNT * (currentPage - 1) + i + 1;
                    if (size > i) {
                        Application application = applications.get(i);
                        writer.write("<tr><th>" + lineNumber + "</th>");
                        writer.write("<td>" + application.getEntityId() + "</td>");
                        writer.write("<td>" + application.getApplicationVacancy().getName() + "</td>");
                        finderLogin = finderService.findFinderLogin(application.getApplicationFinder().getEntityId());
                        writer.write("<td>");
                        if (finderLogin.isPresent()) {
                            writer.write(finderLogin.get());
                        }
                        writer.write("</td>");
                        writer.write("<td><ul class=\"navigate\">");
                        TagUtil.createConfirmApplicationButton(writer, CommandName.CONFIRM_APPLICATION.name(), pageContext, application.getEntityId(), resourceBundle.getString(APPLICATION_OPERATION_CONFIRM));
                        TagUtil.createRejectApplicationButton(writer, CommandName.REJECT_APPLICATION.name(), pageContext, application.getEntityId(), resourceBundle.getString(APPLICATION_OPERATION_REJECT));
                        writer.write("</ul></td>");
                    } else {
                        writer.write("<tr><th>" + lineNumber + "</th>");
                        writer.write("<td></td><td></td><td></td><td></td>");
                    }
                    writer.write("</tr>");
                }
            }
        } catch (IOException | ServiceException e) {
            throw new JspException(e);
        }
    }
}