package innotutor.innotutor_backend.entity.card.enrollment;

import innotutor.innotutor_backend.entity.session.SessionType;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "card_enroll_session_type", schema = "public", catalog = "innotutor")
public class CardEnrollSessionType {
    private Long cardEnrollId;
    private Long sessionTypeId;
    private Long cardEnrollSessionTypeId;
    private CardEnroll cardEnrollByCardEnrollId;
    private SessionType sessionTypeBySessionTypeId;

    public CardEnrollSessionType(final Long cardEnrollId, final Long sessionTypeId, final CardEnroll cardEnrollByCardEnrollId,
                                 final SessionType sessionTypeBySessionTypeId) {
        this.cardEnrollId = cardEnrollId;
        this.sessionTypeId = sessionTypeId;
        this.cardEnrollByCardEnrollId = cardEnrollByCardEnrollId;
        this.sessionTypeBySessionTypeId = sessionTypeBySessionTypeId;
    }

    @Basic
    @Column(name = "card_enroll_id", nullable = false, insertable = false, updatable = false)
    public Long getCardEnrollId() {
        return cardEnrollId;
    }

    public void setCardEnrollId(final Long cardEnrollId) {
        this.cardEnrollId = cardEnrollId;
    }

    @Basic
    @Column(name = "session_type_id", nullable = false, insertable = false, updatable = false)
    public Long getSessionTypeId() {
        return sessionTypeId;
    }

    public void setSessionTypeId(final Long sessionTypeId) {
        this.sessionTypeId = sessionTypeId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_enroll_session_type_id", nullable = false)
    public Long getCardEnrollSessionTypeId() {
        return cardEnrollSessionTypeId;
    }

    public void setCardEnrollSessionTypeId(final Long cardEnrollSessionTypeId) {
        this.cardEnrollSessionTypeId = cardEnrollSessionTypeId;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final CardEnrollSessionType that = (CardEnrollSessionType) object;
        if (!Objects.equals(cardEnrollId, that.cardEnrollId)) {
            return false;
        }
        if (!Objects.equals(sessionTypeId, that.sessionTypeId)) {
            return false;
        }
        return Objects.equals(cardEnrollSessionTypeId, that.cardEnrollSessionTypeId);
    }

    @Override
    public int hashCode() {
        int result = cardEnrollId == null ? 0 : cardEnrollId.hashCode();
        result = 31 * result + (sessionTypeId == null ? 0 : sessionTypeId.hashCode());
        result = 31 * result + (cardEnrollSessionTypeId == null ? 0 : cardEnrollSessionTypeId.hashCode());
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "card_enroll_id", referencedColumnName = "card_enroll_id", nullable = false)
    public CardEnroll getCardEnrollByCardEnrollId() {
        return cardEnrollByCardEnrollId;
    }

    public void setCardEnrollByCardEnrollId(final CardEnroll cardEnrollByCardEnrollId) {
        this.cardEnrollByCardEnrollId = cardEnrollByCardEnrollId;
    }

    @ManyToOne
    @JoinColumn(name = "session_type_id", referencedColumnName = "session_type_id", nullable = false)
    public SessionType getSessionTypeBySessionTypeId() {
        return sessionTypeBySessionTypeId;
    }

    public void setSessionTypeBySessionTypeId(final SessionType sessionTypeBySessionTypeId) {
        this.sessionTypeBySessionTypeId = sessionTypeBySessionTypeId;
    }
}
