package innotutor.innotutor_backend.dto.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {
    private Long sessionId;
    private Long tutorId;
    private List<Long> studentIDsList;
    private String subject;
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSSSS")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSSSS")
    private LocalDateTime endTime;
    private String sessionFormat;
    private String sessionType;
    private String description;
}
