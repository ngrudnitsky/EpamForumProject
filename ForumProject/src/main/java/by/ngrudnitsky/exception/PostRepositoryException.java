package by.ngrudnitsky.exception;

public class PostRepositoryException extends Exception {
    public PostRepositoryException() {
    }

    public PostRepositoryException(String message) {
        super(message);
    }

    public PostRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostRepositoryException(Throwable cause) {
        super(cause);
    }
}
