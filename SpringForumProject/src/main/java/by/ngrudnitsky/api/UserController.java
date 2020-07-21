package by.ngrudnitsky.api;

import by.ngrudnitsky.dto.UserDto;
import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.exception.UserNotFoundException;
import by.ngrudnitsky.mapper.UserMapper;
import by.ngrudnitsky.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/users")
@AllArgsConstructor
@SuppressWarnings("unused")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        try {
            User result = userService.findById(id);
            return new ResponseEntity<>(userMapper.mapToUserDto(result), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
