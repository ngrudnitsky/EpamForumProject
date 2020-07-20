package by.ngrudnitsky.exception;

public class UserRepositoryException extends Exception {
    public UserRepositoryException() {
    }

    public UserRepositoryException(String message) {
        super(message);
    }

    public UserRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserRepositoryException(Throwable cause) {
        super(cause);
    }
}
