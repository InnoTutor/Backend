package innotutor.innotutor_backend.repository.session;

import innotutor.innotutor_backend.entity.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface SessionRepository extends JpaRepository<Session, Long> {
}
