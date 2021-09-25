package innotutor.innotutor_backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "card_session_format", schema = "public", catalog = "innotutor")
public class CardSessionFormat {
    private Long cardId;
    private Long sessionFormatId;
    private Long cardSessionFormatId;
    private Card cardByCardId;
    private SessionFormat sessionFormatBySessionFormatId;

    @Basic
    @Column(name = "card_id", nullable = false, insertable = false, updatable = false)
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    @Basic
    @Column(name = "session_format_id", nullable = false, insertable = false, updatable = false)
    public Long getSessionFormatId() {
        return sessionFormatId;
    }

    public void setSessionFormatId(Long sessionFormatId) {
        this.sessionFormatId = sessionFormatId;
    }

    @Id
    @Column(name = "card_session_format_id", nullable = false)
    public Long getCardSessionFormatId() {
        return cardSessionFormatId;
    }

    public void setCardSessionFormatId(Long cardSessionFormatId) {
        this.cardSessionFormatId = cardSessionFormatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardSessionFormat that = (CardSessionFormat) o;

        if (cardId != null ? !cardId.equals(that.cardId) : that.cardId != null) return false;
        if (sessionFormatId != null ? !sessionFormatId.equals(that.sessionFormatId) : that.sessionFormatId != null)
            return false;
        if (cardSessionFormatId != null ? !cardSessionFormatId.equals(that.cardSessionFormatId) : that.cardSessionFormatId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardId != null ? cardId.hashCode() : 0;
        result = 31 * result + (sessionFormatId != null ? sessionFormatId.hashCode() : 0);
        result = 31 * result + (cardSessionFormatId != null ? cardSessionFormatId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "card_id", nullable = false)
    public Card getCardByCardId() {
        return cardByCardId;
    }

    public void setCardByCardId(Card cardByCardId) {
        this.cardByCardId = cardByCardId;
    }

    @ManyToOne
    @JoinColumn(name = "session_format_id", referencedColumnName = "session_format_id", nullable = false)
    public SessionFormat getSessionFormatBySessionFormatId() {
        return sessionFormatBySessionFormatId;
    }

    public void setSessionFormatBySessionFormatId(SessionFormat sessionFormatBySessionFormatId) {
        this.sessionFormatBySessionFormatId = sessionFormatBySessionFormatId;
    }
}
