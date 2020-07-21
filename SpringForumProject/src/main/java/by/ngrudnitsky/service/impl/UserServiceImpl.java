package by.ngrudnitsky.service.impl;

import by.ngrudnitsky.data.UserRepository;
import by.ngrudnitsky.entity.Status;
import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.exception.UserNotFoundException;
import by.ngrudnitsky.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException {
        User result = userRepository.findByUsername(username).orElse(null);
        if (result == null) {
            String errorMessage = String.format(
                    "IN findByUsername - no user found by username: %s", username);
            log.error(errorMessage);
            throw new UserNotFoundException(errorMessage);
        }
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id) throws UserNotFoundException {
        User result = userRepository.findById(id).orElse(null);
        if (result == null) {
            String errorMessage = String.format(
                    "IN findById - no user found by id: %S", id);
            log.error(errorMessage);
            throw new UserNotFoundException(errorMessage);
        }
        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public User deleteById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            String errorMessage = String.format(
                    "IN delete - no user found by id: %s", id);
            log.error(errorMessage);
            throw new UserNotFoundException(errorMessage);
        }
        user.setStatus(Status.DELETED);
        user.setUpdated(Instant.now());
        User deletedUser = userRepository.save(user);
        log.info("IN delete - user with id: {} successfully deleted", id);
        return deletedUser;
    }
}
