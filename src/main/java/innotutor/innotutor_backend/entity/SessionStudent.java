/*
MIT License

Copyright (c) 2021 InnoTutor

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
