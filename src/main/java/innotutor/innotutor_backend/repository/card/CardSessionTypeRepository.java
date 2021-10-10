package innotutor.innotutor_backend.repository.card;

import innotutor.innotutor_backend.entity.card.CardSessionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CardSessionTypeRepository extends JpaRepository<CardSessionType, Long> {
}
