package by.ngrudnitsky.exception;

import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("unused")
public class EmailServiceException extends AuthenticationException {
    public EmailServiceException(String msg, Throwable t) {
        super(msg, t);
    }

    public EmailServiceException(String msg) {
        super(msg);
    }
}
