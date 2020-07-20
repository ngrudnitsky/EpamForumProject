package by.ngrudnitsky.api;

import by.ngrudnitsky.dto.AdminPostDto;
import by.ngrudnitsky.dto.AdminUserDto;
import by.ngrudnitsky.entity.Post;
import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.exeption.PostServiceException;
import by.ngrudnitsky.exeption.UserNotFoundException;
import by.ngrudnitsky.exeption.UserServiceException;
import by.ngrudnitsky.service.PostService;
import by.ngrudnitsky.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/admin")
@AllArgsConstructor
public class AdminController {
    private final UserService userService;
    private final PostService postService;

    //todo ref class

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable Long id) {
        try {
            User user = userService.findById(id);
            AdminUserDto result = AdminUserDto.fromUser(user);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UserServiceException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<AdminUserDto> deleteUserById(@PathVariable Long id) {
        try {
            User deletedUser = userService.deleteById(id);
            AdminUserDto result = AdminUserDto.fromUser(deletedUser);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UserServiceException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PatchMapping(value = "/posts/update")
    public ResponseEntity<AdminPostDto> updatePost(@RequestBody AdminPostDto postDto) {
        try {
            Post updatedPost = postService.updatePost(postDto.toPost());
            AdminPostDto result = AdminPostDto.fromPost(updatedPost);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (PostServiceException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}