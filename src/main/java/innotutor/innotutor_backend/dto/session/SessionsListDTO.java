package innotutor.innotutor_backend.dto.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionsListDTO {
    private List<SessionDTO> studyingSessions;
    private List<SessionDTO> teachingSessions;
}
