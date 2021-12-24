package innotutor.innotutor_backend.dto.enrollment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyStudentDTO {
    private Long enrollmentId;
    private Long studentId;
    private String studentName;
    private String studentSurname;
    private String studentEmail;
    private String studentContacts;
    private String studentDescription;
    private String studentPicture;
    private Long cardId;
    private String subject;
    private Double rating;
    private Integer countVoted;
    private String description;
    private boolean hidden;
    private List<String> sessionFormat;
    private List<String> sessionType;
}
