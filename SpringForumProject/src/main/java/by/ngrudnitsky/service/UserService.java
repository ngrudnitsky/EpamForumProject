package by.ngrudnitsky.service;

import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findByUsername(String username) throws UserNotFoundException;

    User findById(Long id) throws UserNotFoundException;

    User deleteById(Long id) throws UserNotFoundException;
}
