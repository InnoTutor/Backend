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
public class StudentRequestDTO implements UserCard {
    private Long studentId;
    private String studentName;
    private String studentSurname;
    private Long cardId;
    private String description;
    private String subject;
    private List<String> sessionFormat;
    private List<String> sessionType;
    private Boolean offered;

    @Override
    @JsonIgnore
    @JsonProperty("creatorId")
    public Long getCreatorId() {
        return studentId;
    }

    @Override
    @JsonIgnore
    @JsonProperty("enrolled")
    public Boolean isEnrolled() {
        return offered;
    }
}
