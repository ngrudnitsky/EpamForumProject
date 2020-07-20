package by.ngrudnitsky.controller.command;

import by.ngrudnitsky.controller.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    Logger log = LoggerFactory.getLogger(Command.class);
    Action execute(HttpServletRequest req, HttpServletResponse resp);
}