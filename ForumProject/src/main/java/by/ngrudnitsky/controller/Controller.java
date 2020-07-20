package by.ngrudnitsky.controller;

import org.apache.commons.lang3.EnumUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
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
