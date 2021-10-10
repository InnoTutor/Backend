package innotutor.innotutor_backend.dto.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {
    private Long sessionId;
    private Long tutorId;
    private List<Long> studentIDsList;
    private String subject;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;
    @JsonFormat(pattern = "hh:mm:ss")
    private Time startTime;
    @JsonFormat(pattern = "hh:mm:ss")
    private Time endTime;
    private String sessionFormat;
    private String sessionType;
    private String description;
}
