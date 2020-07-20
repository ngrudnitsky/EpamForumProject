package by.ngrudnitsky.exeption;

import org.springframework.security.core.AuthenticationException;

public class AuthServiceException extends AuthenticationException {
    public AuthServiceException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthServiceException(String msg) {
        super(msg);
    }
}
