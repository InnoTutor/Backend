package innotutor.innotutor_backend.repository.card.enrollment;

import innotutor.innotutor_backend.entity.card.enrollment.CardEnrollSessionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardEnrollSessionTypeRepository extends JpaRepository<CardEnrollSessionType, Long> {
}
