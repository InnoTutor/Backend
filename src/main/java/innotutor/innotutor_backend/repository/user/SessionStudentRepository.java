package innotutor.innotutor_backend.repository.user;

import innotutor.innotutor_backend.entity.user.SessionStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionStudentRepository extends JpaRepository<SessionStudent, Long> {
}
