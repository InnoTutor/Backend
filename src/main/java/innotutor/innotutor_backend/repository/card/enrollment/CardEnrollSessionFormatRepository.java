package innotutor.innotutor_backend.repository.card.enrollment;

import innotutor.innotutor_backend.entity.card.enrollment.CardEnrollSessionFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardEnrollSessionFormatRepository extends JpaRepository<CardEnrollSessionFormat, Long> {
}
