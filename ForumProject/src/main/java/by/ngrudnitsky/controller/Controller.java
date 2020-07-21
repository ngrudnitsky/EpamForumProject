package by.ngrudnitsky.controller;

import by.ngrudnitsky.exception.ConnectionException;
import org.apache.commons.lang3.EnumUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.ngrudnitsky.util.ConnectionPool.initConnectionPool;

public class Controller extends HttpServlet {
    @Override
    public void init() throws ServletException {
        try {
            ServletContext context = getServletContext();
            String url = context.getInitParameter("connection.url");
            String username = context.getInitParameter("connection.username");
            String password = context.getInitParameter("connection.password");
            String initParameter = context.getInitParameter("connection.initial.pool.size");
            initConnectionPool(url, username, password, initParameter);
        } catch (ConnectionException e) {
            throw new ServletException("Error during connection pool initialization", e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        executeCommand(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        executeCommand(req, resp);
    }

    public void executeCommand(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Action currentAction = defineAction(req);
        Action next = currentAction.getCommand().execute(req, resp);
        if (next == null || next.equals(currentAction)) {
            resp.sendRedirect(req.getContextPath() + currentAction.getJspAddress());
        } else {
            resp.sendRedirect("do?command=" + next.name());
        }
    }

    private Action defineAction(HttpServletRequest req) {
        String actionName = req.getParameter("command");
        if (actionName == null || actionName.isEmpty()) {
            return Action.ERROR;
        }
        actionName = actionName.toUpperCase().replace(" ", "_");
        return EnumUtils.getEnum(Action.class, actionName);
    }
}
