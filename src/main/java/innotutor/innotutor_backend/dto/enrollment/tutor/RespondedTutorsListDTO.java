package innotutor.innotutor_backend.dto.enrollment.tutor;

import innotutor.innotutor_backend.dto.enrollment.EnrollmentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondedTutorsListDTO {
    private List<EnrollmentDTO> newTutors;
    private List<EnrollmentDTO> acceptedTutors;
}
