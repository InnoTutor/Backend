package innotutor.innotutor_backend.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Session {
    private Long sessionId;
    private Long tutorId;
    private Long subjectId;
    private Long sessionFormatId;
    private Long sessionTypeId;
    private String date;
    private String startTime;
    private String endTime;
    private String description;
    private Timestamp creationDate;
    private Timestamp lastUpdate;
    private User userByTutorId;
    private Subject subjectBySubjectId;
    private SessionFormat sessionFormatBySessionFormatId;
    private SessionType sessionTypeBySessionTypeId;
    private Collection<SessionStudent> sessionStudentsBySessionId;

    @Id
    @Column(name = "session_id", nullable = false)
    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    @Basic
    @Column(name = "tutor_id", nullable = false, insertable = false, updatable = false)
    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    @Basic
    @Column(name = "subject_id", nullable = false, insertable = false, updatable = false)
    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    @Basic
    @Column(name = "session_format_id", nullable = false, insertable = false, updatable = false)
    public Long getSessionFormatId() {
        return sessionFormatId;
    }

    public void setSessionFormatId(Long sessionFormatId) {
        this.sessionFormatId = sessionFormatId;
    }

    @Basic
    @Column(name = "session_type_id", nullable = false, insertable = false, updatable = false)
    public Long getSessionTypeId() {
        return sessionTypeId;
    }

    public void setSessionTypeId(Long sessionTypeId) {
        this.sessionTypeId = sessionTypeId;
    }

    @Basic
    @Column(name = "date", nullable = false, length = 256)
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Basic
    @Column(name = "start_time", nullable = false, length = 256)
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time", nullable = false, length = 256)
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "creation_date", nullable = true)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "last_update", nullable = true)
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Session session = (Session) o;

        if (sessionId != null ? !sessionId.equals(session.sessionId) : session.sessionId != null) return false;
        if (tutorId != null ? !tutorId.equals(session.tutorId) : session.tutorId != null) return false;
        if (subjectId != null ? !subjectId.equals(session.subjectId) : session.subjectId != null) return false;
        if (sessionFormatId != null ? !sessionFormatId.equals(session.sessionFormatId) : session.sessionFormatId != null)
            return false;
        if (sessionTypeId != null ? !sessionTypeId.equals(session.sessionTypeId) : session.sessionTypeId != null)
            return false;
        if (date != null ? !date.equals(session.date) : session.date != null) return false;
        if (startTime != null ? !startTime.equals(session.startTime) : session.startTime != null) return false;
        if (endTime != null ? !endTime.equals(session.endTime) : session.endTime != null) return false;
        if (description != null ? !description.equals(session.description) : session.description != null) return false;
        if (creationDate != null ? !creationDate.equals(session.creationDate) : session.creationDate != null)
            return false;
        if (lastUpdate != null ? !lastUpdate.equals(session.lastUpdate) : session.lastUpdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sessionId != null ? sessionId.hashCode() : 0;
        result = 31 * result + (tutorId != null ? tutorId.hashCode() : 0);
        result = 31 * result + (subjectId != null ? subjectId.hashCode() : 0);
        result = 31 * result + (sessionFormatId != null ? sessionFormatId.hashCode() : 0);
        result = 31 * result + (sessionTypeId != null ? sessionTypeId.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "tutor_id", referencedColumnName = "user_id", nullable = false)
    public User getUserByTutorId() {
        return userByTutorId;
    }

    public void setUserByTutorId(User userByTutorId) {
        this.userByTutorId = userByTutorId;
    }

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id", nullable = false)
    public Subject getSubjectBySubjectId() {
        return subjectBySubjectId;
    }

    public void setSubjectBySubjectId(Subject subjectBySubjectId) {
        this.subjectBySubjectId = subjectBySubjectId;
    }

    @ManyToOne
    @JoinColumn(name = "session_format_id", referencedColumnName = "session_format_id", nullable = false)
    public SessionFormat getSessionFormatBySessionFormatId() {
        return sessionFormatBySessionFormatId;
    }

    public void setSessionFormatBySessionFormatId(SessionFormat sessionFormatBySessionFormatId) {
        this.sessionFormatBySessionFormatId = sessionFormatBySessionFormatId;
    }

    @ManyToOne
    @JoinColumn(name = "session_type_id", referencedColumnName = "session_type_id", nullable = false)
    public SessionType getSessionTypeBySessionTypeId() {
        return sessionTypeBySessionTypeId;
    }

    public void setSessionTypeBySessionTypeId(SessionType sessionTypeBySessionTypeId) {
        this.sessionTypeBySessionTypeId = sessionTypeBySessionTypeId;
    }

    @OneToMany(mappedBy = "sessionBySessionId")
    public Collection<SessionStudent> getSessionStudentsBySessionId() {
        return sessionStudentsBySessionId;
    }

    public void setSessionStudentsBySessionId(Collection<SessionStudent> sessionStudentsBySessionId) {
        this.sessionStudentsBySessionId = sessionStudentsBySessionId;
    }
}
