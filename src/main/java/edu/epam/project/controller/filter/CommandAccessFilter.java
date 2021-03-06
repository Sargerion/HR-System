package edu.epam.project.controller.filter;

import edu.epam.project.controller.command.*;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.UserType;
import edu.epam.project.model.util.message.ErrorMessage;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/controller"})
public class CommandAccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        UserType userType = UserType.GUEST;
        if (user != null) {
            userType = user.getType();
        }
        String command = httpRequest.getParameter(RequestParameter.COMMAND);
        CommandName commandName;
        try {
            if (command == null) {
                httpRequest.setAttribute(RequestAttribute.ERROR_MESSAGE, ErrorMessage.ERROR_COMMAND);
                RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(PathJsp.ERROR_404_PAGE);
                dispatcher.forward(httpRequest, httpResponse);
                return;
            }
            commandName = CommandName.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            httpRequest.setAttribute(RequestAttribute.ERROR_MESSAGE, ErrorMessage.ERROR_COMMAND);
            RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(PathJsp.ERROR_404_PAGE);
            dispatcher.forward(httpRequest, httpResponse);
            return;
        }
        if (!commandName.isTypeAllowed(userType)) {
            httpRequest.setAttribute(RequestAttribute.ERROR_MESSAGE, ErrorMessage.ERROR_ACCESS);
            RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(PathJsp.ERROR_404_PAGE);
            dispatcher.forward(httpRequest, httpResponse);
            return;
        }
        chain.doFilter(request, response);
    }
}