package innotutor.innotutor_backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "card_session_format", schema = "public", catalog = "innotutor")
public class CardSessionFormat {
    private Integer cardId;
    private Integer sessionFormatId;
    private Card cardByCardId;
    private SessionFormat sessionFormatBySessionFormatId;

    @Basic
    @Column(name = "card_id", nullable = false)
    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    @Basic
    @Column(name = "session_format_id", nullable = false)
    public Integer getSessionFormatId() {
        return sessionFormatId;
    }

    public void setSessionFormatId(Integer sessionFormatId) {
        this.sessionFormatId = sessionFormatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardSessionFormat that = (CardSessionFormat) o;

        if (cardId != null ? !cardId.equals(that.cardId) : that.cardId != null) return false;
        if (sessionFormatId != null ? !sessionFormatId.equals(that.sessionFormatId) : that.sessionFormatId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardId != null ? cardId.hashCode() : 0;
        result = 31 * result + (sessionFormatId != null ? sessionFormatId.hashCode() : 0);
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
