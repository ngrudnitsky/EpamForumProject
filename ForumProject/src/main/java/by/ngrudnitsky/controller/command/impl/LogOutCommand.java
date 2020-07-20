package by.ngrudnitsky.controller.command.impl;

import by.ngrudnitsky.controller.Action;
import by.ngrudnitsky.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutCommand implements Command {
    @Override
    public Action execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        return Action.LOG_IN;
    }
}
