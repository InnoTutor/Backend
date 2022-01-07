package innotutor.innotutor_backend.dto.enrollment.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestedStudentsListInfoDTO {
    private List<MyStudentDTO> newStudents;
    private List<MyStudentDTO> acceptedStudents;
}
