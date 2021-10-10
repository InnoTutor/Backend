package innotutor.innotutor_backend.repository.user;

import innotutor.innotutor_backend.entity.user.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Request findByCardId(Long cardId);

    List<Request> deleteByCardId(Long cardId);

}
