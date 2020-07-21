package by.ngrudnitsky.service;

import by.ngrudnitsky.dto.CommentDto;
import by.ngrudnitsky.exception.PostNotFoundException;
import by.ngrudnitsky.exception.UserNotFoundException;

import java.util.List;

public interface CommentService {
    CommentDto save(CommentDto commentDot) throws PostNotFoundException;

    List<CommentDto> getAllCommentsForPost(Long id) throws PostNotFoundException;

    List<CommentDto> getAllCommentsForUser(Long id) throws UserNotFoundException;
}
