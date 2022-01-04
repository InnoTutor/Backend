package innotutor.innotutor_backend.dto.searcher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TutorCvDTO implements UserCard {
    private Long tutorId;
    private String tutorName;
    private String tutorSurname;
    private Long cardId;
    private Double rating;
    private Integer countVoted;
    private String description;
    private String subject;
    private List<String> sessionFormat;
    private List<String> sessionType;
    private boolean requested;

    @Override
    @JsonIgnore
    @JsonProperty("creatorId")
    public Long getCreatorId() {
        return tutorId;
    }
}
