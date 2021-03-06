package innotutor.innotutor_backend.entity.user;

import innotutor.innotutor_backend.entity.session.Session;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "session_student", schema = "public", catalog = "innotutor")
public class SessionStudent {
    private Long sessionId;
    private Long studentId;
    private Long sessionStudentId;
    private Session sessionBySessionId;
    private User userByStudentId;

    public SessionStudent(final Long sessionId, final Long studentId, final Session sessionBySessionId, final User userByStudentId) {
        this.sessionId = sessionId;
        this.studentId = studentId;
        this.sessionBySessionId = sessionBySessionId;
        this.userByStudentId = userByStudentId;
    }

    @Basic
    @Column(name = "session_id", nullable = false, insertable = false, updatable = false)
    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(final Long sessionId) {
        this.sessionId = sessionId;
    }

    @Basic
    @Column(name = "student_id", nullable = false, insertable = false, updatable = false)
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(final Long studentId) {
        this.studentId = studentId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_student_id", nullable = false)
    public Long getSessionStudentId() {
        return sessionStudentId;
    }

    public void setSessionStudentId(final Long sessionStudentId) {
        this.sessionStudentId = sessionStudentId;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final SessionStudent that = (SessionStudent) object;
        if (!Objects.equals(sessionId, that.sessionId)) {
            return false;
        }
        if (!Objects.equals(studentId, that.studentId)) {
            return false;
        }
        return Objects.equals(sessionStudentId, that.sessionStudentId);
    }

    @Override
    public int hashCode() {
        int result = sessionId == null ? 0 : sessionId.hashCode();
        result = 31 * result + (studentId == null ? 0 : studentId.hashCode());
        result = 31 * result + (sessionStudentId == null ? 0 : sessionStudentId.hashCode());
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "session_id", nullable = false)
    public Session getSessionBySessionId() {
        return sessionBySessionId;
    }

    public void setSessionBySessionId(final Session sessionBySessionId) {
        this.sessionBySessionId = sessionBySessionId;
    }

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_id", nullable = false)
    public User getUserByStudentId() {
        return userByStudentId;
    }

    public void setUserByStudentId(final User userByStudentId) {
        this.userByStudentId = userByStudentId;
    }
}
