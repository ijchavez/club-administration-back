package uy.com.club.administration.exception;

public class UserExistException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;
    public UserExistException(String message) {
        super(null, message, "UserExist", "UserExist");
    }
    public UserExistException() {
        super(null, "User exist exception!", "UserExist", "UserExist");
    }

}
