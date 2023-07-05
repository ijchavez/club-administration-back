package uy.com.club.administration.exception;

public class MissingContributionSuggestedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public MissingContributionSuggestedException(String message) {
        super(null, message, "MissingContributionSuggestedException", "MissingContributionSuggestedException");
    }

    public MissingContributionSuggestedException() {
        super(null, "MissingContributionSuggestedException!", "MissingContributionSuggestedException", "MissingContributionSuggestedException");
    }
}