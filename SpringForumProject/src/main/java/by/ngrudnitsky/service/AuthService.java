package by.ngrudnitsky.service;

import by.ngrudnitsky.dto.*;
import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.exception.UserNotFoundException;

public interface AuthService {
    void verifyAccount(String token);

    UserDto register(RegistrationDto registrationDto);

    AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto) throws UserNotFoundException;

    User getCurrentUser();

    AuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) throws UserNotFoundException;
}
