package by.ngrudnitsky.controller.command.impl;

import by.ngrudnitsky.controller.Action;
import by.ngrudnitsky.controller.command.Command;
import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.exception.ParameterValidationException;
import by.ngrudnitsky.exception.UserServiceException;
import by.ngrudnitsky.service.impl.UserService;
import by.ngrudnitsky.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.ngrudnitsky.util.HttpUtil.*;

public class LogInCommand implements Command {
    private static final String USER_NAME_PATTERN = "[A-Za-z0-9А-Яа-я]+";
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    private final UserService userService = new UserServiceImpl();

    //todo password encoding
    @Override
    public Action execute(HttpServletRequest req, HttpServletResponse resp) {
        String sessionAttribute = "user";
        try {
            if (isMethodPost(req)) {
                String userName = getRequestParameter(req, "username", USER_NAME_PATTERN);
                String password = getRequestParameter(req, "password", PASSWORD_PATTERN);
                User user = userService.findByUsername(userName);
                if (user.getPassword().equals(password)) {
                    HttpSession userSession = setSessionAttribute(req, sessionAttribute, user);
                    userSession.setMaxInactiveInterval(600);
                    HttpSession adminSession = setSessionAttribute(req, "admin",
                            userService.checkIfAdmin(user.getId()) ? "true" : "false");
                    adminSession.setMaxInactiveInterval(600);
                    return Action.PROFILE;
                }
            }
        } catch (ParameterValidationException e) {
            log.error(String.format("IN LogInCommand - failed to validate parameter.%n%s", e.getMessage()));
        } catch (UserServiceException e) {
            log.error(String.format("IN LogInCommand - failed to find user.%n%s", e.getMessage()));
        }
        req.getSession().removeAttribute(sessionAttribute);
        return Action.LOG_IN;
    }
}
