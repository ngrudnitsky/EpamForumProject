package by.ngrudnitsky.service;

import by.ngrudnitsky.dto.UserDto;
import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.exeption.UserNotFoundException;
import by.ngrudnitsky.exeption.UserServiceException;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findByUsername(String username) throws UserNotFoundException;

    User findById(Long id) throws UserNotFoundException;

    User deleteById(Long id) throws UserNotFoundException;
}
