package innotutor.innotutor_backend.repository.card;

import innotutor.innotutor_backend.entity.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findBySubjectId(Long subjectId);

    List<Card> findByHidden(Boolean hidden);
}
