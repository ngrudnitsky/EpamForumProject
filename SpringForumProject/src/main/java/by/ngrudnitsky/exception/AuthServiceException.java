package by.ngrudnitsky.exception;

import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("unused")
public class AuthServiceException extends AuthenticationException {
    public AuthServiceException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthServiceException(String msg) {
        super(msg);
    }
}
