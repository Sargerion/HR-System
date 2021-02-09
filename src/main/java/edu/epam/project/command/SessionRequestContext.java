package edu.epam.project.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SessionRequestContext {

    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;
    private String locale;

    public SessionRequestContext(HttpServletRequest request) {
        requestAttributes = extractRequestAttributes(request);
        requestParameters = extractRequestParameters(request);
        sessionAttributes = extractSessionAttributes(request);
        locale = extractLocale(request);
    }

    public void insertAttributes(HttpServletRequest request) {
        requestAttributes.forEach(request::setAttribute);
        HttpSession session = request.getSession(true);
        sessionAttributes.forEach(session::setAttribute);
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
        if (requestParameters.isEmpty()) {
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
        HttpSession session = request.getSession(true);
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            attributes.put(name, session.getAttribute(name));
        }
        return attributes;
    }

    private String extractLocale(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String currentLocale = (String) session.getAttribute(SessionAttribute.LOCALE);
        return currentLocale != null ? currentLocale : "ru_RU";
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
}