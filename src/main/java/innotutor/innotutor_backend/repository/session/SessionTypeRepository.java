package innotutor.innotutor_backend.repository.session;

import innotutor.innotutor_backend.entity.session.SessionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionTypeRepository extends JpaRepository<SessionType, Long> {
    SessionType findSessionTypeByName(String name);
}
