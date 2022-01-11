package innotutor.innotutor_backend.dto.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionRatingDTO {
    private Long cardId;
    private Integer mark;
}
