package edu.epam.project.controller;

import edu.epam.project.controller.command.*;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.ConnectionException;
import edu.epam.project.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "controller", urlPatterns = {"/controller"})
@MultipartConfig(maxFileSize = 1024 * 1024 * 2,maxRequestSize = 1024 * 1024 * 8)
public class Controller extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionRequestContext requestContext = new SessionRequestContext(request);
        logger.info(requestContext.toString());
        Optional<Command> optionalCommand = CommandProvider.defineCommand(request.getParameter(RequestParameter.COMMAND));
        logger.info(optionalCommand);
        Command command = optionalCommand.orElseThrow(IllegalAccessError::new);
        CommandResult commandResult;
        try {
            commandResult = command.execute(requestContext);
        } catch (CommandException e) {
            logger.error(e);
            throw new ServletException(e);
        }
        logger.info(commandResult.toString());
        requestContext.insertAttributes(request);
        if (request.getParameter(RequestParameter.COMMAND).equals(RequestParameter.LOG_OUT)) {
            request.getSession().invalidate();
        }
        if (commandResult.getTransitionType() == TransitionType.FORWARD) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(commandResult.getPage());
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + commandResult.getPage());
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            ConnectionPool.getInstance().destroyPool();
        } catch (ConnectionException e) {
            logger.error(e);
        }
    }
}