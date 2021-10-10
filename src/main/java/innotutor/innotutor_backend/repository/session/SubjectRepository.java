package innotutor.innotutor_backend.repository.session;

import innotutor.innotutor_backend.entity.session.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findSubjectByName(String name);
}
