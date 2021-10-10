package innotutor.innotutor_backend.dto.enrollment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignedCardsDTO {
    // CV cards to which you requested, but a creator hasn't accepted you yet
    private List<EnrollmentDTO> waitingList;
    // Tutor's classes to which you belong to
    private List<EnrollmentDTO> acceptedList;
}
