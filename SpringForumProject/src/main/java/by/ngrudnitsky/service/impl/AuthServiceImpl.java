package by.ngrudnitsky.service.impl;

import by.ngrudnitsky.data.RoleRepository;
import by.ngrudnitsky.data.UserRepository;
import by.ngrudnitsky.data.VerificationTokenRepository;
import by.ngrudnitsky.dto.*;
import by.ngrudnitsky.entity.NotificationEmail;
import by.ngrudnitsky.entity.Role;
import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.entity.VerificationToken;
import by.ngrudnitsky.exeption.AuthServiceException;
import by.ngrudnitsky.exeption.UserNotFoundException;
import by.ngrudnitsky.exeption.UserServiceException;
import by.ngrudnitsky.mapper.UserMapper;
import by.ngrudnitsky.security.jwt.JwtTokenProvider;
import by.ngrudnitsky.security.jwt.JwtUser;
import by.ngrudnitsky.service.AuthService;
import by.ngrudnitsky.service.MailService;
import by.ngrudnitsky.service.RefreshTokenService;
import by.ngrudnitsky.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final RefreshTokenService refreshTokenService;
    private final MailContentBuilderImpl mailContentBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDto register(RegistrationDto registrationDto) {
        User user = userMapper.mapFromRegistrationDto(registrationDto);
        Role roleUser = roleRepository.findByName("ROLE_USER");
        user.setRoles(List.of(roleUser));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        User registeredUser = userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your account",
                user.getEmail(), mailContentBuilder.build(getEmailMessage(token))));

        log.info("IN register - user: {} successfully registered", registeredUser);
        return userMapper.mapToDto(registeredUser);
    }

    @Override
    public AuthenticationResponseDto login(AuthenticationRequestDto requestDto) throws UserServiceException, UserNotFoundException {
        String username = requestDto.getUsername();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
        User user = userService.findByUsername(username);
        String token = jwtTokenProvider.createToken(username, user.getRoles());
        return AuthenticationResponseDto.builder()
                .token(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .username(username)
                .expiresAt(Instant.now().plusMillis(jwtTokenProvider.getValidityInMilliseconds()))
                .build();
    }

    private String getEmailMessage(String token) {
        return String.format("Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/v1/auth/accountVerification/%s", token);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new AuthServiceException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AuthServiceException("User not found with name - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        JwtUser principal = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    @Override
    public AuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) throws UserNotFoundException {
        @NotBlank String refreshToken = refreshTokenRequestDto.getRefreshToken();
        refreshTokenService.validateRefreshToken(refreshToken);
        String username = refreshTokenRequestDto.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        String token = jwtTokenProvider.createToken(username, user.getRoles());
        return AuthenticationResponseDto.builder()
                .token(token)
                .refreshToken(refreshToken)
                .expiresAt(Instant.now().plusMillis(jwtTokenProvider.getValidityInMilliseconds()))
                .username(refreshTokenRequestDto.getUsername())
                .build();
    }
}