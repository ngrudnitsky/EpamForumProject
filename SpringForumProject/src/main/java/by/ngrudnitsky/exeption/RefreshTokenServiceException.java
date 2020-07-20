package by.ngrudnitsky.exeption;

import org.springframework.security.core.AuthenticationException;

public class RefreshTokenServiceException extends AuthenticationException {
    public RefreshTokenServiceException(String msg, Throwable t) {
        super(msg, t);
    }

    public RefreshTokenServiceException(String msg) {
        super(msg);
    }
}
