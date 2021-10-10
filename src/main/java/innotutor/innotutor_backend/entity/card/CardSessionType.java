package innotutor.innotutor_backend.entity.card;

import innotutor.innotutor_backend.entity.session.SessionType;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "card_session_type", schema = "public", catalog = "innotutor")
public class CardSessionType {
    private Long cardId;
    private Long sessionTypeId;
    private Long cardSessionTypeId;
    private Card cardByCardId;
    private SessionType sessionTypeBySessionTypeId;

    public CardSessionType(final Long cardId, final Long sessionTypeId, final Card cardByCardId, final SessionType sessionTypeBySessionTypeId) {
        this.cardId = cardId;
        this.sessionTypeId = sessionTypeId;
        this.cardByCardId = cardByCardId;
        this.sessionTypeBySessionTypeId = sessionTypeBySessionTypeId;
    }

    @Basic
    @Column(name = "card_id", nullable = false, insertable = false, updatable = false)
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(final Long cardId) {
        this.cardId = cardId;
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
    @Column(name = "card_session_type_id", nullable = false)
    public Long getCardSessionTypeId() {
        return cardSessionTypeId;
    }

    public void setCardSessionTypeId(final Long cardSessionTypeId) {
        this.cardSessionTypeId = cardSessionTypeId;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final CardSessionType that = (CardSessionType) object;
        if (!Objects.equals(cardId, that.cardId)) {
            return false;
        }
        if (!Objects.equals(sessionTypeId, that.sessionTypeId)) {
            return false;
        }
        return Objects.equals(cardSessionTypeId, that.cardSessionTypeId);
    }

    @Override
    public int hashCode() {
        int result = cardId == null ? 0 : cardId.hashCode();
        result = 31 * result + (sessionTypeId == null ? 0 : sessionTypeId.hashCode());
        result = 31 * result + (cardSessionTypeId == null ? 0 : cardSessionTypeId.hashCode());
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "card_id", nullable = false)
    public Card getCardByCardId() {
        return cardByCardId;
    }

    public void setCardByCardId(final Card cardByCardId) {
        this.cardByCardId = cardByCardId;
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
