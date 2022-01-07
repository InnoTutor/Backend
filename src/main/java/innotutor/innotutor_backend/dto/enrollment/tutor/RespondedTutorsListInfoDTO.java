package innotutor.innotutor_backend.dto.enrollment.tutor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondedTutorsListInfoDTO {
    private List<MyTutorDTO> newTutors;
    private List<MyTutorDTO> acceptedTutors;
}
