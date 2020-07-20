package by.ngrudnitsky.exeption;

public class VoteServiceException extends Exception {
    public VoteServiceException() {
    }

    public VoteServiceException(String message) {
        super(message);
    }

    public VoteServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public VoteServiceException(Throwable cause) {
        super(cause);
    }
}
