package innotutor.innotutor_backend.repository.user;

import innotutor.innotutor_backend.entity.user.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> deleteByCardId(Long cardId);

    Service findByCardId(Long cardId);
}
