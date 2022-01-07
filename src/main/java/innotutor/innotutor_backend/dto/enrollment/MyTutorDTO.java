package innotutor.innotutor_backend.dto.enrollment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyTutorDTO {
    private Long enrollmentId;
    private Long tutorId;
    private String tutorName;
    private String tutorSurname;
    private String tutorEmail;
    private String tutorContacts;
    private String tutorDescription;
    private String tutorPicture;
    private Long cardId;
    private String subject;
    private String cardDescription;
    private Boolean hidden;
    private String enrollmentDescription;
    private List<String> sessionFormat;
    private List<String> sessionType;
}
