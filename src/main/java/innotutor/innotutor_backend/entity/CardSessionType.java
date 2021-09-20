package innotutor.innotutor_backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "card_session_type", schema = "public", catalog = "innotutor")
public class CardSessionType {
    private Integer cardId;
    private Integer sessionTypeId;
    private Card cardByCardId;
    private SessionType sessionTypeBySessionTypeId;

    @Basic
    @Column(name = "card_id", nullable = false)
    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    @Basic
    @Column(name = "session_type_id", nullable = false)
    public Integer getSessionTypeId() {
        return sessionTypeId;
    }

    public void setSessionTypeId(Integer sessionTypeId) {
        this.sessionTypeId = sessionTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardSessionType that = (CardSessionType) o;

        if (cardId != null ? !cardId.equals(that.cardId) : that.cardId != null) return false;
        if (sessionTypeId != null ? !sessionTypeId.equals(that.sessionTypeId) : that.sessionTypeId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardId != null ? cardId.hashCode() : 0;
        result = 31 * result + (sessionTypeId != null ? sessionTypeId.hashCode() : 0);
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
    @JoinColumn(name = "session_type_id", referencedColumnName = "session_type_id", nullable = false)
    public SessionType getSessionTypeBySessionTypeId() {
        return sessionTypeBySessionTypeId;
    }

    public void setSessionTypeBySessionTypeId(SessionType sessionTypeBySessionTypeId) {
        this.sessionTypeBySessionTypeId = sessionTypeBySessionTypeId;
    }
}
