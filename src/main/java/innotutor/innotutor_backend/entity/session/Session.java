package innotutor.innotutor_backend.entity.session;

import innotutor.innotutor_backend.entity.user.SessionStudent;
import innotutor.innotutor_backend.entity.user.User;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "session", schema = "public", catalog = "innotutor")
public class Session { // NOPMD - suppressed GodClass
    // NOPMD - suppressed TooManyFields
    // It's not a GodClass since it represents only entity from DB
    private final static String SESSION_BY_SESSION_ID = "sessionBySessionId";
    private Long sessionId;
    private Long tutorId;
    private Long subjectId;
    private Long sessionFormatId;
    private Long sessionTypeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private Timestamp creationDate;
    private Timestamp lastUpdate;
    private User userByTutorId;
    private Subject subjectBySubjectId;
    private SessionFormat sessionFormatBySessionFormatId;
    private SessionType sessionTypeBySessionTypeId;
    private Collection<SessionStudent> sessionStudentsBySessionId;
    private Collection<SessionRating> sessionRatingsBySessionId;

    public Session(final Long tutorId,
                   final Long subjectId,
                   final Long sessionFormatId,
                   final Long sessionTypeId,
                   final LocalDateTime startTime,
                   final LocalDateTime endTime,
                   final String description,
                   final User userByTutorId,
                   final Subject subjectBySubjectId,
                   final SessionFormat sessionFormatBySessionFormatId,
                   final SessionType sessionTypeBySessionTypeId) {
        this.tutorId = tutorId;
        this.subjectId = subjectId;
        this.sessionFormatId = sessionFormatId;
        this.sessionTypeId = sessionTypeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.userByTutorId = userByTutorId;
        this.subjectBySubjectId = subjectBySubjectId;
        this.sessionFormatBySessionFormatId = sessionFormatBySessionFormatId;
        this.sessionTypeBySessionTypeId = sessionTypeBySessionTypeId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id", nullable = false)
    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(final Long sessionId) {
        this.sessionId = sessionId;
    }

    @Basic
    @Column(name = "tutor_id", nullable = false, insertable = false, updatable = false)
    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(final Long tutorId) {
        this.tutorId = tutorId;
    }

    @Basic
    @Column(name = "subject_id", nullable = false, insertable = false, updatable = false)
    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(final Long subjectId) {
        this.subjectId = subjectId;
    }

    @Basic
    @Column(name = "session_format_id", nullable = false, insertable = false, updatable = false)
    public Long getSessionFormatId() {
        return sessionFormatId;
    }

    public void setSessionFormatId(final Long sessionFormatId) {
        this.sessionFormatId = sessionFormatId;
    }

    @Basic
    @Column(name = "session_type_id", nullable = false, insertable = false, updatable = false)
    public Long getSessionTypeId() {
        return sessionTypeId;
    }

    public void setSessionTypeId(final Long sessionTypeId) {
        this.sessionTypeId = sessionTypeId;
    }

    @Basic
    @Column(name = "start_time", nullable = false)
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(final LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time", nullable = false)
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(final LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "description", length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Basic
    @CreationTimestamp
    @Column(name = "creation_date", insertable = false, updatable = false)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @UpdateTimestamp
    @Column(name = "last_update", insertable = false)
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(final Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Session session = (Session) object;
        if (!Objects.equals(sessionId, session.sessionId)) {
            return false;
        }
        if (!Objects.equals(tutorId, session.tutorId)) {
            return false;
        }
        if (!Objects.equals(subjectId, session.subjectId)) {
            return false;
        }
        if (!Objects.equals(sessionFormatId, session.sessionFormatId)) {
            return false;
        }
        if (!Objects.equals(sessionTypeId, session.sessionTypeId)) {
            return false;
        }
        if (!Objects.equals(startTime, session.startTime)) {
            return false;
        }
        if (!Objects.equals(endTime, session.endTime)) {
            return false;
        }
        if (!Objects.equals(description, session.description)) {
            return false;
        }
        if (!Objects.equals(creationDate, session.creationDate)) {
            return false;
        }
        return Objects.equals(lastUpdate, session.lastUpdate);
    }

    @Override
    public int hashCode() {
        int result = sessionId == null ? 0 : sessionId.hashCode();
        result = 31 * result + (tutorId == null ? 0 : tutorId.hashCode());
        result = 31 * result + (subjectId == null ? 0 : subjectId.hashCode());
        result = 31 * result + (sessionFormatId == null ? 0 : sessionFormatId.hashCode());
        result = 31 * result + (sessionTypeId == null ? 0 : sessionTypeId.hashCode());
        result = 31 * result + (startTime == null ? 0 : startTime.hashCode());
        result = 31 * result + (endTime == null ? 0 : endTime.hashCode());
        result = 31 * result + (description == null ? 0 : description.hashCode());
        result = 31 * result + (creationDate == null ? 0 : creationDate.hashCode());
        result = 31 * result + (lastUpdate == null ? 0 : lastUpdate.hashCode());
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "tutor_id", referencedColumnName = "user_id", nullable = false)
    public User getUserByTutorId() {
        return userByTutorId;
    }

    public void setUserByTutorId(final User userByTutorId) {
        this.userByTutorId = userByTutorId;
    }

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id", nullable = false)
    public Subject getSubjectBySubjectId() {
        return subjectBySubjectId;
    }

    public void setSubjectBySubjectId(final Subject subjectBySubjectId) {
        this.subjectBySubjectId = subjectBySubjectId;
    }

    @ManyToOne
    @JoinColumn(name = "session_format_id", referencedColumnName = "session_format_id", nullable = false)
    public SessionFormat getSessionFormatBySessionFormatId() {
        return sessionFormatBySessionFormatId;
    }

    public void setSessionFormatBySessionFormatId(final SessionFormat sessionFormatBySessionFormatId) {
        this.sessionFormatBySessionFormatId = sessionFormatBySessionFormatId;
    }

    @ManyToOne
    @JoinColumn(name = "session_type_id", referencedColumnName = "session_type_id", nullable = false)
    public SessionType getSessionTypeBySessionTypeId() {
        return sessionTypeBySessionTypeId;
    }

    public void setSessionTypeBySessionTypeId(final SessionType sessionTypeBySessionTypeId) {
        this.sessionTypeBySessionTypeId = sessionTypeBySessionTypeId;
    }

    @OneToMany(mappedBy = SESSION_BY_SESSION_ID, cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Collection<SessionStudent> getSessionStudentsBySessionId() {
        return sessionStudentsBySessionId;
    }

    public void setSessionStudentsBySessionId(final Collection<SessionStudent> sessionStudentsBySessionId) {
        this.sessionStudentsBySessionId = sessionStudentsBySessionId;
    }

    @OneToMany(mappedBy = SESSION_BY_SESSION_ID)
    public Collection<SessionRating> getSessionRatingsBySessionId() {
        return sessionRatingsBySessionId;
    }

    public void setSessionRatingsBySessionId(final Collection<SessionRating> sessionRatingsBySessionId) {
        this.sessionRatingsBySessionId = sessionRatingsBySessionId;
    }
}
