package by.ngrudnitsky.service;

import by.ngrudnitsky.dto.*;
import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.exeption.UserNotFoundException;
import by.ngrudnitsky.exeption.UserServiceException;

public interface AuthService {
    void verifyAccount(String token);

    UserDto register(RegistrationDto registrationDto);

    AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto) throws UserServiceException, UserNotFoundException;

    User getCurrentUser();

    AuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) throws UserNotFoundException;
}
