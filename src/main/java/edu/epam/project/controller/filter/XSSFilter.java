package edu.epam.project.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Enumeration;

@WebFilter(filterName = "XSSFilter")
public class XSSFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Enumeration<String> allAttributes = request.getAttributeNames();
        while (allAttributes.hasMoreElements()) {
            String parameterName = allAttributes.nextElement();
            String parameterValue = request.getParameter(parameterName);
            if (parameterValue != null) {
                String newParameterValue = parameterValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                request.setAttribute(parameterName, newParameterValue);
            }
        }
        chain.doFilter(request, response);
    }
}