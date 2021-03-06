package by.ngrudnitsky.exception;

import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("unused")
public class RefreshTokenServiceException extends AuthenticationException {
    public RefreshTokenServiceException(String msg, Throwable t) {
        super(msg, t);
    }

    public RefreshTokenServiceException(String msg) {
        super(msg);
    }
}
