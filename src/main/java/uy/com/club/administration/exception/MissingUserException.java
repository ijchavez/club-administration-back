package uy.com.club.administration.exception;

public class MissingUserException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;
    public MissingUserException(String message) {
        super(null, message, "MissingUserException", "MissingUserException");
    }
    public MissingUserException() {
        super(null, "MissingUserException!", "MissingUserException", "MissingUserException");
    }
}
