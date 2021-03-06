package by.ngrudnitsky.exception;

import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("unused")
public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
