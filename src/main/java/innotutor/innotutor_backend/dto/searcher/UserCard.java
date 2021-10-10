package innotutor.innotutor_backend.dto.searcher;

import java.util.List;

public interface UserCard {
    Long getCreatorId();

    String getSubject();

    List<String> getSessionFormat();

    List<String> getSessionType();
}
