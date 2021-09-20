package innotutor.innotutor_backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "card_enroll_session_type", schema = "public", catalog = "innotutor")
public class CardEnrollSessionType {
    private Integer cardEnrollId;
    private Integer sessionTypeId;
    private Card cardByCardEnrollId;
    private SessionType sessionTypeBySessionTypeId;

    @Basic
    @Column(name = "card_enroll_id", nullable = false)
    public Integer getCardEnrollId() {
        return cardEnrollId;
    }

    public void setCardEnrollId(Integer cardEnrollId) {
        this.cardEnrollId = cardEnrollId;
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

        CardEnrollSessionType that = (CardEnrollSessionType) o;

        if (cardEnrollId != null ? !cardEnrollId.equals(that.cardEnrollId) : that.cardEnrollId != null) return false;
        if (sessionTypeId != null ? !sessionTypeId.equals(that.sessionTypeId) : that.sessionTypeId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardEnrollId != null ? cardEnrollId.hashCode() : 0;
        result = 31 * result + (sessionTypeId != null ? sessionTypeId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "card_enroll_id", referencedColumnName = "card_id", nullable = false)
    public Card getCardByCardEnrollId() {
        return cardByCardEnrollId;
    }

    public void setCardByCardEnrollId(Card cardByCardEnrollId) {
        this.cardByCardEnrollId = cardByCardEnrollId;
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
