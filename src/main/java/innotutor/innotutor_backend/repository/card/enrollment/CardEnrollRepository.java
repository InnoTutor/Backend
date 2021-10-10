package innotutor.innotutor_backend.repository.card.enrollment;

import innotutor.innotutor_backend.entity.card.enrollment.CardEnroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardEnrollRepository extends JpaRepository<CardEnroll, Long> {
    List<CardEnroll> findByUserId(Long userId);
}
