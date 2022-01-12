package innotutor.innotutor_backend.entity.session;

import innotutor.innotutor_backend.entity.user.User;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "session_rating", schema = "public", catalog = "innotutor")
public class SessionRating {
    private Long sessionRatingId;
    private Long sessionId;
    private Long userId;
    private Integer mark;
    private String feedback;
    private Timestamp creationDate;
    private Timestamp lastUpdate;
    private Session sessionBySessionId;
    private User userByUserId;

    public SessionRating(final Long sessionId, final Long userId, final Integer mark, final String feedback,
                         final Session sessionBySessionId, final User userByUserId) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.mark = mark;
        this.feedback = feedback;
        this.sessionBySessionId = sessionBySessionId;
        this.userByUserId = userByUserId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_rating_id", nullable = false)
    public Long getSessionRatingId() {
        return sessionRatingId;
    }

    public void setSessionRatingId(final Long sessionRatingId) {
        this.sessionRatingId = sessionRatingId;
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
    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "mark", nullable = false)
    public Integer getMark() {
        return mark;
    }

    public void setMark(final Integer mark) {
        this.mark = mark;
    }

    @Basic
    @Column(name = "feedback", length = 256)
    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(final String feedback) {
        this.feedback = feedback;
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
        final SessionRating that = (SessionRating) object;
        if (!Objects.equals(sessionRatingId, that.sessionRatingId)) {
            return false;
        }
        if (!Objects.equals(sessionId, that.sessionId)) {
            return false;
        }
        if (!Objects.equals(userId, that.userId)) {
            return false;
        }
        if (!Objects.equals(mark, that.mark)) {
            return false;
        }
        if (!Objects.equals(feedback, that.feedback)) {
            return false;
        }
        if (!Objects.equals(creationDate, that.creationDate)) {
            return false;
        }
        return Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        int result = sessionRatingId == null ? 0 : sessionRatingId.hashCode();
        result = 31 * result + (sessionId == null ? 0 : sessionId.hashCode());
        result = 31 * result + (userId == null ? 0 : userId.hashCode());
        result = 31 * result + (mark == null ? 0 : mark.hashCode());
        result = 31 * result + (feedback == null ? 0 : feedback.hashCode());
        result = 31 * result + (creationDate == null ? 0 : creationDate.hashCode());
        result = 31 * result + (lastUpdate == null ? 0 : lastUpdate.hashCode());
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
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(final User userByUserId) {
        this.userByUserId = userByUserId;
    }
}
