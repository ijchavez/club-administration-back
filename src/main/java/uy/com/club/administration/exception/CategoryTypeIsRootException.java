package uy.com.club.administration.exception;

public class CategoryTypeIsRootException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public CategoryTypeIsRootException(String message) {
        super(null, message, "CategoryTypeIsRootException", "CategoryTypeIsRootException");
    }

    public CategoryTypeIsRootException() {
        super(null, "CategoryTypeIsRootException!", "CategoryTypeIsRootException", "CategoryTypeIsRootException");
    }
}
