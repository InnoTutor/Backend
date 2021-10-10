package innotutor.innotutor_backend.entity.card;

import innotutor.innotutor_backend.entity.session.SessionFormat;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "card_session_format", schema = "public", catalog = "innotutor")
public class CardSessionFormat {
    private Long cardId;
    private Long sessionFormatId;
    private Long cardSessionFormatId;
    private Card cardByCardId;
    private SessionFormat sessionFormatBySessionFormatId;

    public CardSessionFormat(final Long cardId, final Long sessionFormatId, final Card cardByCardId, final SessionFormat sessionFormatBySessionFormatId) {
        this.cardId = cardId;
        this.sessionFormatId = sessionFormatId;
        this.cardByCardId = cardByCardId;
        this.sessionFormatBySessionFormatId = sessionFormatBySessionFormatId;
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
    @Column(name = "session_format_id", nullable = false, insertable = false, updatable = false)
    public Long getSessionFormatId() {
        return sessionFormatId;
    }

    public void setSessionFormatId(final Long sessionFormatId) {
        this.sessionFormatId = sessionFormatId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_session_format_id", nullable = false)
    public Long getCardSessionFormatId() {
        return cardSessionFormatId;
    }

    public void setCardSessionFormatId(final Long cardSessionFormatId) {
        this.cardSessionFormatId = cardSessionFormatId;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final CardSessionFormat that = (CardSessionFormat) object;
        if (!Objects.equals(cardId, that.cardId)) {
            return false;
        }
        if (!Objects.equals(sessionFormatId, that.sessionFormatId)) {
            return false;
        }
        return Objects.equals(cardSessionFormatId, that.cardSessionFormatId);
    }

    @Override
    public int hashCode() {
        int result = cardId == null ? 0 : cardId.hashCode();
        result = 31 * result + (sessionFormatId == null ? 0 : sessionFormatId.hashCode());
        result = 31 * result + (cardSessionFormatId == null ? 0 : cardSessionFormatId.hashCode());
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
    @JoinColumn(name = "session_format_id", referencedColumnName = "session_format_id", nullable = false)
    public SessionFormat getSessionFormatBySessionFormatId() {
        return sessionFormatBySessionFormatId;
    }

    public void setSessionFormatBySessionFormatId(final SessionFormat sessionFormatBySessionFormatId) {
        this.sessionFormatBySessionFormatId = sessionFormatBySessionFormatId;
    }
}
