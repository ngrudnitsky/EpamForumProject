package by.ngrudnitsky.service.impl;

import by.ngrudnitsky.data.RoleRepository;
import by.ngrudnitsky.data.UserRepository;
import by.ngrudnitsky.data.impl.RoleRepositoryImpl;
import by.ngrudnitsky.data.impl.UserRepositoryImpl;
import by.ngrudnitsky.entity.Status;
import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final RoleRepository roleRepository = new RoleRepositoryImpl();
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User register(User user) throws UserServiceException {
        checkIfValueIsNull(user, "IN UserServiceImpl.register - user is null");
        try {
            user.setStatus(Status.ACTIVE);
            user.setCreated(new Date());
            user.setUpdated(new Date());
            user = userRepository.create(user);
            roleRepository.setRoleUser(user.getId());
            log.info("IN UserServiceImpl.register - user: {} successfully registered", user.getUserName());
            return user;
        } catch (UserRepositoryException e) {
            String errorMessage = "IN UserServiceImpl.register - failed to register user";
            log.error(errorMessage);
            throw new UserServiceException(errorMessage, e);
        } catch (RoleRepositoryException e) {
            String errorMessage = "IN UserServiceImpl.register - failed to set USER role for user";
            log.error(errorMessage);
            throw new UserServiceException(errorMessage, e);
        }
    }

    //todo findFromTo
    @Override
    public List<User> findAll() throws UserServiceException {
        try {
            List<User> result = userRepository.findAll();
            log.info("IN UserServiceImpl.findAll - {} users found", result.size());
            return result;
        } catch (UserRepositoryException e) {
            String errorMessage = "IN UserServiceImpl.findAll - failed to get all users";
            log.error(errorMessage);
            throw new UserServiceException(errorMessage, e);
        }
    }

    @Override
    public User findByUsername(String userName) throws UserServiceException {
        checkIfValueIsNull(userName, "IN UserServiceImpl.findByUsername - Null userName was found");
        try {
            User foundUser = userRepository.findByUsername(userName);
            log.info("IN UserServiceImpl.findByUsername - found user by userName {}", userName);
            return foundUser;
        } catch (UserRepositoryException e) {
            String errorMessage = String.format(
                    "IN UserServiceImpl.findByUsername - Failed to find user by userName %s", userName);
            log.error(errorMessage);
            throw new UserServiceException(errorMessage, e);
        }
    }

    @Override
    public User findById(Integer id) throws UserServiceException {
        checkIfValueIsNull(id, "IN UserServiceImpl.findById - Null id was found");
        try {
            User foundUser = userRepository.findById(id);
            log.info("IN UserServiceImpl.findById - found user by id {}", id);
            return foundUser;
        } catch (UserRepositoryException e) {
            String errorMessage = String.format("IN UserServiceImpl.findById - Failed to find user by id %s", id);
            log.error(errorMessage);
            throw new UserServiceException(errorMessage, e);
        }
    }

    @Override
    public User deleteById(Integer id) throws UserServiceException {
        checkIfValueIsNull(id, "IN UserServiceImpl.deleteById - Null id was found");
        try {
            User user = userRepository.deleteById(id);
            log.info("IN UserServiceImpl.delete - user with id: {} successfully deleted", id);
            return user;
        } catch (UserRepositoryException e) {
            String errorMessage = String.format("IN UserServiceImpl.deleteById - Failed to delete user by id %s", id);
            log.error(errorMessage);
            throw new UserServiceException(errorMessage, e);
        }
    }

    @Override
    public boolean checkIfAdmin(Integer id) throws UserServiceException {
        checkIfValueIsNull(id, "IN UserServiceImpl.delete - Null id was found");
        try {
            List<String> userRoles = roleRepository.findAllUserRoles(id);
            return userRoles.contains("2");
        } catch (RoleRepositoryException e) {
            String errorMessage =
                    String.format(
                            "IN UserServiceImpl.checkIfAdmin - Failed to check if user %S is admin.", id);
            log.error(errorMessage);
            throw new UserServiceException(errorMessage, e);
        }
    }

    @Override
    public Integer getLastId() throws PostServiceException {
        String errorMessage = "IN UserServiceImpl.getLastId - Failed to get last id";
        try {
            Integer id = userRepository.getLastId();
            if (id == -1) {
                throw new PostServiceException(errorMessage);
            }
            log.info("IN UserServiceImpl.getLastId - id: {} was successfully got", id);
            return id;
        } catch (UserRepositoryException e) {
            log.error(errorMessage);
            throw new PostServiceException(errorMessage, e);
        }
    }

    private void checkIfValueIsNull(Object value, String errorMessage) throws UserServiceException {
        if (value == null) {
            log.error(errorMessage);
            throw new UserServiceException(errorMessage);
        }
    }
}
