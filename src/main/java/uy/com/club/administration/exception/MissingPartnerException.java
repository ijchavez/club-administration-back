package uy.com.club.administration.exception;

public class MissingPartnerException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public MissingPartnerException(String message) {
        super(null, message, "MissingPartner", "MissingPartner");
    }

    public MissingPartnerException() {
        super(null, "Missing Partner exception! not exist this partner with this id", "MissingPartner", "MissingPartner");
    }
}