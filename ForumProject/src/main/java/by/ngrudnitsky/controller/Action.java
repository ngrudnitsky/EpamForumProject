package by.ngrudnitsky.controller;

import by.ngrudnitsky.controller.command.Command;
import by.ngrudnitsky.controller.command.impl.*;

public enum Action {
    PROFILE("/profile", new ProfileCommand()),
    WRITING("/writing", new WritePostCommand()),
    JOIN("/join", new SignUpCommand()),
    LOG_IN("/login", new LogInCommand()),
    POST("/post", new PostCommand()),
    LOG_OUT("", new LogOutCommand()),
    MAIN_PAGE("/feed", new MainPageCommand()),
    EDIT_POST("/edit", new EditPostsCommand()),
    ERROR("/error", new WrongCommand());

    private final String jspAddress;
    private final Command command;

    Action(String jspAddress, Command command) {
        this.jspAddress = jspAddress;
        this.command = command;
    }

    public String getJspAddress() {
        return jspAddress;
    }

    public Command getCommand() {
        return command;
    }
}
