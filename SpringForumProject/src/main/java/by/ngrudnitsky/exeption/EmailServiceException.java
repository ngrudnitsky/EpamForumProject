package by.ngrudnitsky.exeption;

import org.springframework.security.core.AuthenticationException;

public class EmailServiceException extends AuthenticationException {
    public EmailServiceException(String msg, Throwable t) {
        super(msg, t);
    }

    public EmailServiceException(String msg) {
        super(msg);
    }
}
