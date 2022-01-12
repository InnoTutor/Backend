package innotutor.innotutor_backend.repository.session;

import innotutor.innotutor_backend.entity.session.SessionRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface SessionRatingRepository extends JpaRepository<SessionRating, Long> {
}
