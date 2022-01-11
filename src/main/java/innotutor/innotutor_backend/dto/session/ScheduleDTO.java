package innotutor.innotutor_backend.dto.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private List<SessionDTO> studyingSessionsConducted;
    private List<SessionDTO> studyingSessionsUpcoming;
    private List<SessionDTO> teachingSessionsConducted;
    private List<SessionDTO> teachingSessionsUpcoming;
}
