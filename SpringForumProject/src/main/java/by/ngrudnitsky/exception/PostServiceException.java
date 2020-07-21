package by.ngrudnitsky.exception;

@SuppressWarnings("unused")
public class PostServiceException extends Exception {
    public PostServiceException() {
    }

    public PostServiceException(String message) {
        super(message);
    }

    public PostServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostServiceException(Throwable cause) {
        super(cause);
    }
}
