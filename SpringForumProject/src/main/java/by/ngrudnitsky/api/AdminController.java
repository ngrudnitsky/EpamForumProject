package by.ngrudnitsky.api;

import by.ngrudnitsky.dto.AdminUserDto;
import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.exeption.UserNotFoundException;
import by.ngrudnitsky.exeption.UserServiceException;
import by.ngrudnitsky.mapper.UserMapper;
import by.ngrudnitsky.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/admin")
@AllArgsConstructor
@SuppressWarnings("unused")
public class AdminController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable Long id) {
        try {
            User result = userService.findById(id);
            return new ResponseEntity<>(userMapper.mapToAdminUserDto(result), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<AdminUserDto> deleteUserById(@PathVariable Long id) {
        try {
            User result = userService.deleteById(id);
            return new ResponseEntity<>(userMapper.mapToAdminUserDto(result), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}