package by.ngrudnitsky.service;

import by.ngrudnitsky.entity.RefreshToken;

public interface RefreshTokenService {
    RefreshToken generateRefreshToken();

    void deleteRefreshToken(String token);

    void validateRefreshToken(String token);
}
