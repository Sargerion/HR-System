package edu.epam.project.tag.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class TagUtil {

    private static final String PAGE_PROPERTIES_PATH = "property/pagecontent";

    private TagUtil() {
    }

    public static ResourceBundle getLocalizeText(String locale) {
        String language = locale.substring(0, locale.indexOf("_"));
        String country = locale.substring(locale.indexOf("_") + 1);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(PAGE_PROPERTIES_PATH, new Locale(language, country));
        return resourceBundle;
    }

    public static void paginate(PageContext pageContext, int pagesCount, String command) throws JspException {
        try {
            JspWriter writer = pageContext.getOut();
            String contextPath = pageContext.getServletContext().getContextPath();
            writer.write("<form method=\"post\" action=\"" + contextPath + "/controller\">");
            writer.write("<input type=\"hidden\" name=\"command\" value=\"" + command + "\"/>");
            writer.write("<ul class=\"navigate\">");
            for (int i = 0; i < pagesCount; i++) {
                createButton(writer, i + 1);
            }
            writer.write("</ul>");
            writer.write("</form>");
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    public static void createTableHeadItem(JspWriter writer, String content) throws JspException {
        try {
            writer.write("<th>" + content + "</th>");
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    public static void showImage(PageContext pageContext, String imageName) throws IOException {
        JspWriter writer = pageContext.getOut();
        String contextPath = pageContext.getServletContext().getContextPath();
        writer.write("<img width=\"55\" height=\"55\" style=\"border-radius: 50%; margin-left: 11px; margin-top: 8px;\" src=\"" + contextPath + "/" + imageName + "\">");
    }

    public static void createApplyVacancyButton(JspWriter writer, String command, PageContext pageContext, int vacancyId, String buttonText) throws IOException {
        String contextPath = pageContext.getServletContext().getContextPath();
        writer.write("<form method=\"post\" action=\"" + contextPath + "/controller\">");
        writer.write("<input type=\"hidden\" name=\"command\" value=\"" + command + "\"/>");
        writer.write("<button class=\"apply_button\" type=\"submit\" name=\"applyVacancyButton\" ");
        writer.write("value=\"" + vacancyId + "\">");
        writer.write(buttonText + "</button></form>");
    }

    public static void createConfirmApplicationButton(JspWriter writer, String command, PageContext pageContext, int applicationId, String buttonText) throws IOException {
        String contextPath = pageContext.getServletContext().getContextPath();
        writer.write("<li><form method=\"post\" action=\"" + contextPath + "/controller\">");
        writer.write("<input type=\"hidden\" name=\"command\" value=\"" + command + "\"/>");
        writer.write("<button class=\"apply_button\" type=\"submit\" name=\"confirmApplicationButton\" ");
        writer.write("value=\"" + applicationId + "\">");
        writer.write(buttonText + "</button></form></li>");
    }

    public static void createRejectApplicationButton(JspWriter writer, String command, PageContext pageContext, int applicationId, String buttonText) throws IOException {
        String contextPath = pageContext.getServletContext().getContextPath();
        writer.write("<li><form method=\"post\" action=\"" + contextPath + "/controller\">");
        writer.write("<input type=\"hidden\" name=\"command\" value=\"" + command + "\"/>");
        writer.write("<button class=\"apply_button\" type=\"submit\" name=\"rejectApplicationButton\" ");
        writer.write("value=\"" + applicationId + "\">");
        writer.write(buttonText + "</button></form></li>");
    }

    public static void createBlockButton(JspWriter writer, String command, PageContext pageContext, int userId, String buttonText) throws IOException {
        String contextPath = pageContext.getServletContext().getContextPath();
        writer.write("<form method=\"post\" action=\"" + contextPath + "/controller\">");
        writer.write("<input type=\"hidden\" name=\"command\" value=\"" + command + "\"/>");
        writer.write("<button class=\"apply_button\" type=\"submit\" name=\"blockUserButton\" ");
        writer.write("value=\"" + userId + "\">");
        writer.write(buttonText + "</button></form>");
    }

    public static void createUnblockButton(JspWriter writer, String command, PageContext pageContext, int userId, String buttonText) throws IOException {
        String contextPath = pageContext.getServletContext().getContextPath();
        writer.write("<form method=\"post\" action=\"" + contextPath + "/controller\">");
        writer.write("<input type=\"hidden\" name=\"command\" value=\"" + command + "\"/>");
        writer.write("<button class=\"apply_button\" type=\"submit\" name=\"unblockUserButton\" ");
        writer.write("value=\"" + userId + "\">");
        writer.write(buttonText + "</button></form>");
    }

    private static void createButton(JspWriter writer, int pageNumber) throws IOException {
        writer.write("<li><button type=\"submit\" name=\"newPage\" ");
        writer.write("value=\"" + pageNumber + "\" ");
        writer.write("style=\""
                + "background-color: #ffffffb8; color: #000" + "\">");
        writer.write(pageNumber + "</button></li>");
    }
}