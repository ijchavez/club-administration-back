package uy.com.club.administration.exception;

public class MissingContributionException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public MissingContributionException(String message) {
        super(null, message, "MissingContributionException", "MissingContributionException");
    }

    public MissingContributionException() {
        super(null, "MissingContributionException!", "MissingContributionException", "MissingContributionException");
    }
}