package innotutor.innotutor_backend.repository.card;

import innotutor.innotutor_backend.entity.card.CardSessionFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CardSessionFormatRepository extends JpaRepository<CardSessionFormat, Long> {
}
