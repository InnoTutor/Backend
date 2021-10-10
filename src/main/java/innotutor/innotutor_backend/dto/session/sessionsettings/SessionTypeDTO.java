package innotutor.innotutor_backend.dto.session.sessionsettings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionTypeDTO {
    private Long sessionTypeId;
    private String name;
}
