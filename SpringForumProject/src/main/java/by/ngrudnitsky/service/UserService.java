package by.ngrudnitsky.service;

import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.exeption.UserNotFoundException;
import by.ngrudnitsky.exeption.UserServiceException;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findByUsername(String username) throws UserServiceException, UserNotFoundException;

    User findById(Long id) throws UserServiceException, UserNotFoundException;

    User deleteById(Long id) throws UserServiceException, UserNotFoundException;
}
