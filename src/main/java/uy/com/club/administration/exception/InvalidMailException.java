package uy.com.club.administration.exception;

public class InvalidMailException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;
    public InvalidMailException(String message) {
        super(null, message, "InvalidMail", "InvalidMail");
    }
    public InvalidMailException() {
        super(null, "Email invalid!", "InvalidMail", "InvalidMail");
    }

}
