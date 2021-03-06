package innotutor.innotutor_backend.dto.enrollment.student;

import innotutor.innotutor_backend.dto.enrollment.EnrollmentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestedStudentsListDTO {
    private List<EnrollmentDTO> newStudents;
    private List<EnrollmentDTO> acceptedStudents;
}
