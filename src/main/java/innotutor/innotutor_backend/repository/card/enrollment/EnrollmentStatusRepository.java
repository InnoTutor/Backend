package innotutor.innotutor_backend.repository.card.enrollment;

import innotutor.innotutor_backend.entity.card.enrollment.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentStatusRepository extends JpaRepository<EnrollmentStatus, Long> {
    EnrollmentStatus findEnrollmentStatusByStatus(String status);
}
