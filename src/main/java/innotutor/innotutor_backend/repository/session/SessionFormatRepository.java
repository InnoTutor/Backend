package innotutor.innotutor_backend.repository.session;

import innotutor.innotutor_backend.entity.session.SessionFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionFormatRepository extends JpaRepository<SessionFormat, Long> {
    SessionFormat findSessionFormatByName(String name);
}
