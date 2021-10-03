package innotutor.innotutor_backend.DTO.enrollment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentStatusDTO {
    private Long enrollmentId;
    private String status;
}
