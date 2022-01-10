package innotutor.innotutor_backend.dto.enrollment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaitingListDTO {
    private List<EnrollmentDTO> studentsRequested;
    private List<EnrollmentDTO> studentsRejected;
    private List<EnrollmentDTO> tutorsRequested;
    private List<EnrollmentDTO> tutorsRejected;
}
