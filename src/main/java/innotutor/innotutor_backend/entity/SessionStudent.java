package innotutor.innotutor_backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "session_student", schema = "public", catalog = "innotutor")
public class SessionStudent {
    private Long sessionId;
    private Long studentId;
    private Long sessionStudentId;
    private Session sessionBySessionId;
    private User userByStudentId;

    @Basic
    @Column(name = "session_id", nullable = false, insertable = false, updatable = false)
    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    @Basic
    @Column(name = "student_id", nullable = false, insertable = false, updatable = false)
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Id
    @Column(name = "session_student_id", nullable = false)
    public Long getSessionStudentId() {
        return sessionStudentId;
    }

    public void setSessionStudentId(Long sessionStudentId) {
        this.sessionStudentId = sessionStudentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionStudent that = (SessionStudent) o;

        if (sessionId != null ? !sessionId.equals(that.sessionId) : that.sessionId != null) return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;
        if (sessionStudentId != null ? !sessionStudentId.equals(that.sessionStudentId) : that.sessionStudentId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sessionId != null ? sessionId.hashCode() : 0;
        result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
        result = 31 * result + (sessionStudentId != null ? sessionStudentId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "session_id", nullable = false)
    public Session getSessionBySessionId() {
        return sessionBySessionId;
    }

    public void setSessionBySessionId(Session sessionBySessionId) {
        this.sessionBySessionId = sessionBySessionId;
    }

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_id", nullable = false)
    public User getUserByStudentId() {
        return userByStudentId;
    }

    public void setUserByStudentId(User userByStudentId) {
        this.userByStudentId = userByStudentId;
    }
}
