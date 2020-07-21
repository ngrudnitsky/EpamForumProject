package by.ngrudnitsky.api;

import by.ngrudnitsky.dto.*;
import by.ngrudnitsky.exception.UserNotFoundException;
import by.ngrudnitsky.service.AuthService;
import by.ngrudnitsky.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/auth")
@AllArgsConstructor
@SuppressWarnings("unused")
public class AuthenticationController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createUser(@RequestBody RegistrationDto regUserDto) {
        UserDto user = authService.register(regUserDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            AuthenticationResponseDto response = authService.login(requestDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<AuthenticationResponseDto> refreshToken(
            @Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        try {
            AuthenticationResponseDto response = authService.refreshToken(refreshTokenRequestDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequestDto.getRefreshToken());
        return new ResponseEntity<>("Refresh Token Deleted Successfully!", HttpStatus.OK);
    }
}
