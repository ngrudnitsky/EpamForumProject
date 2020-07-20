package by.ngrudnitsky.api;

import by.ngrudnitsky.dto.CommentDto;
import by.ngrudnitsky.exeption.PostNotFoundException;
import by.ngrudnitsky.exeption.UserNotFoundException;
import by.ngrudnitsky.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        try {
            CommentDto createdComment = commentService.save(commentDto);
            return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/by-post/{id}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long id) {
        try {
            List<CommentDto> comments = commentService.getAllCommentsForPost(id);
            return new ResponseEntity<>(comments, HttpStatus.CREATED);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/by-user/{id}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForUser(@PathVariable Long id) {
        try {
            List<CommentDto> comments = commentService.getAllCommentsForUser(id);
            return new ResponseEntity<>(comments, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
