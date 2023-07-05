package uy.com.club.administration.exception;

public class MissingCategoryTypeException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;
    public MissingCategoryTypeException(String message) {
        super(null, message, "MissingCategoryTypeException", "MissingCategoryTypeException");
    }
    public MissingCategoryTypeException() {
        super(null, "MissingCategoryTypeException!", "MissingCategoryTypeException", "MissingCategoryTypeException");
    }
}
