package innotutor.innotutor_backend.service.utility.card;

public class ValidSessionRating {

    private final Integer mark;
    private final int LOW_MARK = 0;
    private final int HIGH_MARK = 5;

    public ValidSessionRating(final Integer mark) {
        this.mark = mark;
    }

    public boolean isValid() {
        return mark != null && mark >= LOW_MARK && mark <= HIGH_MARK;
    }
}
