package innotutor.innotutor_backend.entity.card.enrollment;

import innotutor.innotutor_backend.entity.session.SessionFormat;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "card_enroll_session_format", schema = "public", catalog = "innotutor")
public class CardEnrollSessionFormat {
    private Long cardEnrollId;
    private Long sessionFormatId;
    private Long cardEnrollSessionFormatId;
    private CardEnroll cardEnrollByCardEnrollId;
    private SessionFormat sessionFormatBySessionFormatId;

    public CardEnrollSessionFormat(final Long cardEnrollId, final Long sessionFormatId, final CardEnroll cardEnrollByCardEnrollId,
                                   final SessionFormat sessionFormatBySessionFormatId) {
        this.cardEnrollId = cardEnrollId;
        this.sessionFormatId = sessionFormatId;
        this.cardEnrollByCardEnrollId = cardEnrollByCardEnrollId;
        this.sessionFormatBySessionFormatId = sessionFormatBySessionFormatId;
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
    @Column(name = "session_format_id", nullable = false, insertable = false, updatable = false)
    public Long getSessionFormatId() {
        return sessionFormatId;
    }

    public void setSessionFormatId(final Long sessionFormatId) {
        this.sessionFormatId = sessionFormatId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_enroll_session_format_id", nullable = false)
    public Long getCardEnrollSessionFormatId() {
        return cardEnrollSessionFormatId;
    }

    public void setCardEnrollSessionFormatId(final Long cardEnrollSessionFormatId) {
        this.cardEnrollSessionFormatId = cardEnrollSessionFormatId;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final CardEnrollSessionFormat that = (CardEnrollSessionFormat) object;
        if (!Objects.equals(cardEnrollId, that.cardEnrollId)) {
            return false;
        }
        if (!Objects.equals(sessionFormatId, that.sessionFormatId)) {
            return false;
        }
        return Objects.equals(cardEnrollSessionFormatId, that.cardEnrollSessionFormatId);
    }

    @Override
    public int hashCode() {
        int result = cardEnrollId == null ? 0 : cardEnrollId.hashCode();
        result = 31 * result + (sessionFormatId == null ? 0 : sessionFormatId.hashCode());
        result = 31 * result + (cardEnrollSessionFormatId == null ? 0 : cardEnrollSessionFormatId.hashCode());
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
    @JoinColumn(name = "session_format_id", referencedColumnName = "session_format_id", nullable = false)
    public SessionFormat getSessionFormatBySessionFormatId() {
        return sessionFormatBySessionFormatId;
    }

    public void setSessionFormatBySessionFormatId(final SessionFormat sessionFormatBySessionFormatId) {
        this.sessionFormatBySessionFormatId = sessionFormatBySessionFormatId;
    }
}
