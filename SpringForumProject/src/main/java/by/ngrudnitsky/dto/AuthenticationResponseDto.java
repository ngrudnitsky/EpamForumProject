package by.ngrudnitsky.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class AuthenticationResponseDto {
    private String username;
    private String token;
    private String refreshToken;
    private Instant expiresAt;
}
