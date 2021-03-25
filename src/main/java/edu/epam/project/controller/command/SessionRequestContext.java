package edu.epam.project.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class-Wrapper to encapsulate a user request and move it in the program.
 * @author Sargerion.
 */
public class SessionRequestContext {

    private static final Logger logger = LogManager.getLogger();
    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;
    private String locale;
    private List<Part> fileParts;
    private boolean isLogout;

    public SessionRequestContext(HttpServletRequest request) {
        requestAttributes = extractRequestAttributes(request);
        requestParameters = extractRequestParameters(request);
        sessionAttributes = extractSessionAttributes(request);
        locale = extractLocale(request);
        fileParts = extractFileParts(request);
    }

    public void insertAttributes(HttpServletRequest request) {
        requestAttributes.forEach(request::setAttribute);
        HttpSession session = request.getSession();
        sessionAttributes.forEach(session::setAttribute);
        if (isLogout) {
            session.invalidate();
        }
    }

    public String getLocale() {
        return locale;
    }

    public Object getRequestAttribute(String key) {
        return requestAttributes.get(key);
    }

    public void setRequestAttribute(String key, Object value) {
        requestAttributes.put(key, value);
    }

    public Optional<String> getRequestParameter(String key) {
        if (requestParameters.isEmpty() || !requestParameters.containsKey(key)) {
            return Optional.empty();
        }
        return Optional.of(requestParameters.get(key)[0]);
    }

    public void setRequestParameters(Map<String, String[]> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public Object getSessionAttribute(String key) {
        return sessionAttributes.get(key);
    }

    public void setSessionAttribute(String key, Object value) {
        sessionAttributes.put(key, value);
    }

    public List<Part> getFileParts() {
        return fileParts;
    }

    public boolean isLogout() {
        return isLogout;
    }

    public void setLogout(boolean logout) {
        isLogout = logout;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SessionRequestContext{");
        sb.append("requestAttributes=").append(requestAttributes);
        sb.append(", requestParameters=").append(requestParameters);
        sb.append(", sessionAttributes=").append(sessionAttributes);
        sb.append(", locale='").append(locale).append('\'');
        sb.append('}');
        return sb.toString();
    }

    private Map<String, Object> extractRequestAttributes(HttpServletRequest request) {
        Map<String, Object> attributes = new HashMap<>();
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            attributes.put(name, request.getAttribute(name));
        }
        return attributes;
    }

    private Map<String, String[]> extractRequestParameters(HttpServletRequest request) {
        return request.getParameterMap();
    }

    private Map<String, Object> extractSessionAttributes(HttpServletRequest request) {
        Map<String, Object> attributes = new HashMap<>();
        HttpSession session = request.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            attributes.put(name, session.getAttribute(name));
        }
        return attributes;
    }

    private String extractLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String currentLocale = (String) session.getAttribute(SessionAttribute.LOCALE);
        return currentLocale != null ? currentLocale : "ru_RU";
    }

    private List<Part> extractFileParts(HttpServletRequest request) {
        List<Part> fileParts = new ArrayList<>();
        try {
            if (request.getContentType() != null && request.getContentType().toLowerCase().contains("multipart/form-data")) {
                fileParts.addAll(request.getParts());
            }
        } catch (IOException | ServletException e) {
            logger.error(e);
        }
        return fileParts;
    }
}