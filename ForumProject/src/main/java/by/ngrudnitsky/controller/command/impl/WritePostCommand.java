package by.ngrudnitsky.controller.command.impl;

import by.ngrudnitsky.controller.Action;
import by.ngrudnitsky.controller.command.Command;
import by.ngrudnitsky.entity.Post;
import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.entity.dto.PostDTO;
import by.ngrudnitsky.exception.ParameterValidationException;
import by.ngrudnitsky.exception.PostServiceException;
import by.ngrudnitsky.service.impl.PostService;
import by.ngrudnitsky.service.impl.PostServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.ngrudnitsky.util.HttpUtil.*;

public class WritePostCommand implements Command {
    private final PostService postService = new PostServiceImpl();

    @Override
    public Action execute(HttpServletRequest req, HttpServletResponse resp) {
        if (isMethodPost(req)) {
            PostDTO post = createNewPost(req);
            if (post != null) {
                setSessionAttribute(req, "post", post);
                setSessionAttribute(req, "postId", post.getId());
                return Action.POST;
            }
        }
        return Action.WRITING;
    }

    private PostDTO createNewPost(HttpServletRequest req) {
        try {
            Post post = new Post();
            post.setTitle(getRequestParameter(req, "title"));
            post.setPreview(getRequestParameter(req, "preview"));
            post.setContent(getRequestParameter(req, "content"));
            User user = (User) req.getSession().getAttribute("user");
            post.setUserId(user.getId());
            postService.create(post);
            PostDTO postDto = PostDTO.convertToPostDTO(post);
            postDto.setUser(user);
            return postDto;
        } catch (ParameterValidationException e) {
            log.error("IN WriteCommand.createNewPost - failed to validate parameter", e);
        } catch (PostServiceException e) {
            log.error("IN WriteCommand.createNewPost - failed to create new post", e);
        }
        return null;
    }
}
