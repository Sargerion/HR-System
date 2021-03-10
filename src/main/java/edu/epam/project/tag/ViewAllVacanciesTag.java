package edu.epam.project.tag;

import edu.epam.project.controller.command.CommandName;
import edu.epam.project.controller.command.SessionAttribute;
import edu.epam.project.controller.command.SessionRequestContext;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.UserType;
import edu.epam.project.model.entity.Vacancy;
import edu.epam.project.model.service.ApplicationService;
import edu.epam.project.model.service.impl.ApplicationServiceImpl;
import edu.epam.project.tag.util.TagUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class ViewAllVacanciesTag extends TagSupport {

    public static final int LIST_LINES_COUNT = 10;
    private static final String VACANCY_ID_BUNDLE = "all_vacancies_table_id";
    private static final String VACANCY_NAME_BUNDLE = "all_vacancies_table_name";
    private static final String VACANCY_SPECIALTY_NAME_BUNDLE = "all_vacancies_table_specialty_name";
    private static final String VACANCY_SALARY_BUNDLE = "all_vacancies_table_salary";
    private static final String VACANCY_WORK_EXPERIENCE_BUNDLE = "all_vacancies_table_work_experience";
    private static final String VACANCY_COMPANY_NAME = "all_vacancies_table_company_name";
    private static final String VACANCY_AVAILABILITY = "all_vacancies_table_is_active";
    private static final String APPLY_SENT = "all_vacancies_table_apply_sent";
    private static final String VACANCY_ACTIVE_STATUS = "all_vacancies_table_status_value_active";
    private static final String VACANCY_BLOCKED_STATUS = "all_vacancies_table_status_value_block";
    private static final String APPLY_BUTTON_TEXT = "all_vacancies_table_apply_text";

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        JspWriter writer = pageContext.getOut();
        SessionRequestContext sessionRequestContext = new SessionRequestContext(request);
        createList(writer, sessionRequestContext);
        int vacanciesCount = (int) sessionRequestContext.getSessionAttribute(SessionAttribute.VACANCIES_COUNT);
        int pagesCount = vacanciesCount % LIST_LINES_COUNT == 0 ? vacanciesCount / LIST_LINES_COUNT : vacanciesCount / LIST_LINES_COUNT + 1;
        String command = CommandName.FIND_VACANCY_LIST.name();
        TagUtil.paginate(pageContext, pagesCount, command);
        return SKIP_BODY;
    }

    private void createList(JspWriter writer, SessionRequestContext sessionRequestContext) throws JspException {
        try {
            writer.write("<table id=\"customers\">");
            createTableHeader(writer, sessionRequestContext);
            writer.write("<tbody>");
            List<Vacancy> vacancies = (List<Vacancy>) sessionRequestContext.getSessionAttribute(SessionAttribute.VACANCY_PAGINATE_LIST);
            User user = (User) sessionRequestContext.getSessionAttribute(SessionAttribute.USER);
            UserType userType = user.getType();
            switch (userType) {
                case ADMIN, COMPANY_HR -> {
                    createLines(writer, sessionRequestContext, vacancies);
                }
                case FINDER -> {
                    createLines(writer, sessionRequestContext, vacancies, user.getEntityId());
                }
            }
            writer.write("</tbody></table>");
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    private void createTableHeader(JspWriter writer, SessionRequestContext sessionRequestContext) throws JspException {
        try {
            String locale = sessionRequestContext.getLocale();
            ResourceBundle resourceBundle = TagUtil.getLocalizeText(locale);
            String vacancyID = resourceBundle.getString(VACANCY_ID_BUNDLE);
            String vacancyName = resourceBundle.getString(VACANCY_NAME_BUNDLE);
            String vacancySpecialtyName = resourceBundle.getString(VACANCY_SPECIALTY_NAME_BUNDLE);
            String vacancySalary = resourceBundle.getString(VACANCY_SALARY_BUNDLE);
            String vacancyNeedWorkExperience = resourceBundle.getString(VACANCY_WORK_EXPERIENCE_BUNDLE);
            String vacancyCompanyName = resourceBundle.getString(VACANCY_COMPANY_NAME);
            String vacancyAvailability = resourceBundle.getString(VACANCY_AVAILABILITY);
            writer.write("<thead><tr>");
            writer.write("<th><span style=\"font-weight: bold\">№</span></th>");
            TagUtil.createTableHeadItem(writer, vacancyID);
            TagUtil.createTableHeadItem(writer, vacancyName);
            TagUtil.createTableHeadItem(writer, vacancySpecialtyName);
            TagUtil.createTableHeadItem(writer, vacancySalary);
            TagUtil.createTableHeadItem(writer, vacancyNeedWorkExperience);
            TagUtil.createTableHeadItem(writer, vacancyCompanyName);
            TagUtil.createTableHeadItem(writer, vacancyAvailability);
            writer.write("</tr></thead>");
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    private void createLines(JspWriter writer, SessionRequestContext sessionRequestContext, List<Vacancy> vacancies) throws JspException {
        String locale = sessionRequestContext.getLocale();
        ResourceBundle resourceBundle = TagUtil.getLocalizeText(locale);
        try {
            if (vacancies != null) {
                int size = vacancies.size();
                int currentPage = (int) sessionRequestContext.getSessionAttribute(SessionAttribute.VACANCY_LIST_CURRENT_PAGE);
                for (int i = 0; i < LIST_LINES_COUNT; i++) {
                    int lineNumber = LIST_LINES_COUNT * (currentPage - 1) + i + 1;
                    if (size > i) {
                        Vacancy vacancy = vacancies.get(i);
                        writer.write("<tr><th>" + lineNumber + "</th>");
                        writer.write("<td>" + vacancy.getEntityId() + "</td>");
                        writer.write("<td>" + vacancy.getName() + "</td>");
                        writer.write("<td>" + vacancy.getSpecialty().getSpecialtyName() + "</td>");
                        writer.write("<td>" + vacancy.getSalary() + "</td>");
                        writer.write("<td>" + vacancy.getNeedWorkExperience() + "</td>");
                        writer.write("<td>" + vacancy.getVacancyCompany().getName() + "</td>");
                        writer.write("<td>");
                        if (vacancy.isVacancyActive()) {
                            writer.write(resourceBundle.getString(VACANCY_ACTIVE_STATUS));
                        } else {
                            writer.write(resourceBundle.getString(VACANCY_BLOCKED_STATUS));
                        }
                        writer.write("</td>");
                    } else {
                        writer.write("<tr><th>" + lineNumber + "</th>");
                        writer.write("<td></td><td></td><td></td><td></td><td></td><td></td><td></td>");
                    }
                    writer.write("</tr>");
                }
            }
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    private void createLines(JspWriter writer, SessionRequestContext sessionRequestContext, List<Vacancy> vacancies, Integer finderId) throws JspException {
        ApplicationService applicationService = ApplicationServiceImpl.getInstance();
        String locale = sessionRequestContext.getLocale();
        ResourceBundle resourceBundle = TagUtil.getLocalizeText(locale);
        try {
            if (vacancies != null) {
                int size = vacancies.size();
                int currentPage = (int) sessionRequestContext.getSessionAttribute(SessionAttribute.VACANCY_LIST_CURRENT_PAGE);
                for (int i = 0; i < LIST_LINES_COUNT; i++) {
                    int lineNumber = LIST_LINES_COUNT * (currentPage - 1) + i + 1;
                    if (size > i) {
                        Vacancy vacancy = vacancies.get(i);
                        writer.write("<tr><th>" + lineNumber + "</th>");
                        writer.write("<td>" + vacancy.getEntityId() + "</td>");
                        writer.write("<td>" + vacancy.getName() + "</td>");
                        writer.write("<td>" + vacancy.getSpecialty().getSpecialtyName() + "</td>");
                        writer.write("<td>" + vacancy.getSalary() + "</td>");
                        writer.write("<td>" + vacancy.getNeedWorkExperience() + "</td>");
                        writer.write("<td>" + vacancy.getVacancyCompany().getName() + "</td>");
                        writer.write("<td>");
                        if (!applicationService.isFinderApply(vacancy.getEntityId(), finderId)) {
                            String command = CommandName.APPLY_VACANCY.name();
                            writer.write("<ul class=\"navigate\"><li>");
                            TagUtil.createApplyVacancyButton(writer, command, pageContext, vacancy.getEntityId(), resourceBundle.getString(APPLY_BUTTON_TEXT));
                            writer.write("</li></ul>");
                        } else {
                            writer.write(resourceBundle.getString(APPLY_SENT));
                        }
                        writer.write("</td>");
                    } else {
                        writer.write("<tr><th>" + lineNumber + "</th>");
                        writer.write("<td></td><td></td><td></td><td></td><td></td><td></td><td></td>");
                    }
                    writer.write("</tr>");
                }
            }
        } catch (IOException | ServiceException e) {
            throw new JspException(e);
        }
    }
}