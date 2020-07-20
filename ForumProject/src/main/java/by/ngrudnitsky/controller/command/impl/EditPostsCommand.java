package by.ngrudnitsky.controller.command.impl;


import by.ngrudnitsky.controller.Action;
import by.ngrudnitsky.controller.command.Command;
import by.ngrudnitsky.entity.Post;
import by.ngrudnitsky.exception.ParameterValidationException;
import by.ngrudnitsky.exception.PostServiceException;
import by.ngrudnitsky.service.impl.PostService;
import by.ngrudnitsky.service.impl.PostServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.ngrudnitsky.util.HttpUtil.*;


public class EditPostsCommand implements Command {
    private final PostService postService = new PostServiceImpl();

    @Override
    public Action execute(HttpServletRequest req, HttpServletResponse resp) {
        if (isMethodPost(req)) {
            try {
                Integer id = Integer.parseInt(getRequestParameter(req, "id"));
                if (isRequestParameterPresented(req, "update")) {
                    updatePost(req, postService, id);
                }
                if (isRequestParameterPresented(req, "delete")) {
                    deletePost(postService, id);
                }
            } catch (ParameterValidationException e) {
                log.error("IN EditPostsCommand - failed to validate parameter.", e);
            }
        }
        loadAllPosts(req);
        return Action.EDIT_POST;
    }

    private void deletePost(PostService postService, Integer id) {
        try {
            postService.deleteById(id);
        } catch (PostServiceException e) {
            log.error("IN EditPostsCommand - failed to register user.");
        }
    }

    private void updatePost(HttpServletRequest req, PostService postService, Integer id) throws ParameterValidationException {
        try {
            Post updatedPost = postService.findById(id);
            String title = getRequestParameter(req, "title");
            String content = getRequestParameter(req, "content");
            updatedPost.setTitle(title);
            updatedPost.setContent(content);
            postService.updatePost(updatedPost);
        } catch (PostServiceException e) {
            log.error("IN EditPostsCommand - failed to update post.");
        }
    }

    private void loadAllPosts(HttpServletRequest req) {
        try {
            List<Post> posts = postService.findAll();
            HttpSession session = req.getSession();
            session.setAttribute("posts", posts);
        } catch (PostServiceException e) {
            log.error("IN EditPostsCommand - failed to load all posts");
        }
    }
}