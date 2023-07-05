package uy.com.club.administration.exception;

public class MissingPaymentCategoryException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;
    public MissingPaymentCategoryException(String message) {
        super(null, message, "MissingPaymentCategoryException", "MissingPaymentCategoryException");
    }
    public MissingPaymentCategoryException() {
        super(null, "MissingPaymentCategoryException!", "MissingPaymentCategoryException", "MissingPaymentCategoryException");
    }
}
