package by.ngrudnitsky.security;

import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.exeption.UserNotFoundException;
import by.ngrudnitsky.exeption.UserServiceException;
import by.ngrudnitsky.security.jwt.JwtUser;
import by.ngrudnitsky.security.jwt.JwtUserFactory;
import by.ngrudnitsky.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;


    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userService.findByUsername(username);
            JwtUser jwtUser = JwtUserFactory.create(user);
            log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
            return jwtUser;
        } catch (UserServiceException | UserNotFoundException e) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }
    }
}
