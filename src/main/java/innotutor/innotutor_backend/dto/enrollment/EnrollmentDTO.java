package innotutor.innotutor_backend.dto.enrollment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {
    private Long enrollmentId;
    private Long enrollerId;
    private Long cardId;
    private List<String> sessionFormat;
    private List<String> sessionType;
}
