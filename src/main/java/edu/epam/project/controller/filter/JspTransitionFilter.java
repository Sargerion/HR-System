package edu.epam.project.controller.filter;

import edu.epam.project.controller.command.PathJsp;
import edu.epam.project.controller.command.SessionAttribute;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.UserType;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/*"})
public class JspTransitionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        UserType userType = UserType.GUEST;
        String page;
        String uri = httpRequest.getRequestURI();
        if (user != null) {
            userType = user.getType();
            page = definePage(userType);
        } else {
            page = PathJsp.HOME_PAGE;
        }
        if (uri.contains(PathJsp.ADMIN_URL_PART) && (userType != UserType.ADMIN)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + page);
            return;
        } else if (uri.contains(PathJsp.HR_URL_PART) && (userType != UserType.COMPANY_HR)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + page);
            return;
        } else if (uri.contains(PathJsp.FINDER_URL_PART) && (userType != UserType.FINDER)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + page);
            return;
        } else if (uri.contains(PathJsp.COMMON_URL_PART) && (userType == UserType.GUEST)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + page);
            return;
        } else if (uri.contains(PathJsp.ERROR_URL_PART) || uri.contains(PathJsp.MODULES_URL_PART)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + page);
            return;
        } else if (uri.contains(PathJsp.GUEST_URL_PART) && (userType != UserType.GUEST)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + page);
            return;
        }
        chain.doFilter(request, response);
    }

    private String definePage(UserType userType) {
        String page = null;
        switch (userType) {
            case ADMIN -> {
                page = PathJsp.ADMIN_PAGE;
            }
            case COMPANY_HR -> {
                page = PathJsp.HR_PAGE;
            }
            case FINDER -> {
                page = PathJsp.FINDER_PAGE;
            }
        }
        return page;
    }
}