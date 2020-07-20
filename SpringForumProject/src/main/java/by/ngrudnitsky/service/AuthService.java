package by.ngrudnitsky.service;

import by.ngrudnitsky.dto.AuthenticationResponseDto;
import by.ngrudnitsky.dto.RefreshTokenRequestDto;
import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.exeption.UserNotFoundException;

public interface AuthService {
    public void verifyAccount(String token);

    public User register(User user);

    User getCurrentUser();

    AuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) throws UserNotFoundException;
}
