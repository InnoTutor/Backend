package innotutor.innotutor_backend.dto.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
    private Long cardId;
    private Long creatorId;
    private String subject;
    private Double rating;
    private Integer countVoted;
    private String description;
    private boolean hidden;
    private List<String> sessionFormat;
    private List<String> sessionType;
}
