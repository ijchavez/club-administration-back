package uy.com.club.administration.exception;

public class MissingMemberException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;
    public MissingMemberException(String message) {
        super(null, message, "MissingMemberException", "MissingMemberException");
    }
    public MissingMemberException() {
        super(null, "MissingMemberException!", "MissingMemberException", "MissingMemberException");
    }
}
