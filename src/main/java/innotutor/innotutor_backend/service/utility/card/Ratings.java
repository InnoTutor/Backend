package innotutor.innotutor_backend.service.utility.card;

import innotutor.innotutor_backend.entity.session.Session;
import innotutor.innotutor_backend.entity.session.SessionRating;
import innotutor.innotutor_backend.entity.user.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Ratings {

    private final List<Integer> marks;

    public Ratings(final User tutor, final Long subjectId) {
        this.marks = tutor.getSessionsByUserId()
                .stream()
                .filter(session -> session.getSubjectId().equals(subjectId))
                .map(Session::getSessionRatingsBySessionId)
                .flatMap(Collection::stream)
                .map(SessionRating::getMark)
                .collect(Collectors.toList());
    }

    public Double averageRating() {
        if (marks.isEmpty()) {
            return null;
        }
        double sum = 0;
        for (final Integer mark : marks) {
            sum += mark;
        }
        return sum / marks.size();
    }

    public Integer countVoted() {
        return marks.size();
    }
}
