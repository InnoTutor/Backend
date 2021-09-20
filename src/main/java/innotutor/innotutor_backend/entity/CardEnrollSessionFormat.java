package innotutor.innotutor_backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "card_enroll_session_format", schema = "public", catalog = "innotutor")
public class CardEnrollSessionFormat {
    private Integer cardEnrollId;
    private Integer sessionFormatId;
    private Card cardByCardEnrollId;
    private SessionFormat sessionFormatBySessionFormatId;

    @Basic
    @Column(name = "card_enroll_id", nullable = false)
    public Integer getCardEnrollId() {
        return cardEnrollId;
    }

    public void setCardEnrollId(Integer cardEnrollId) {
        this.cardEnrollId = cardEnrollId;
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

        CardEnrollSessionFormat that = (CardEnrollSessionFormat) o;

        if (cardEnrollId != null ? !cardEnrollId.equals(that.cardEnrollId) : that.cardEnrollId != null) return false;
        if (sessionFormatId != null ? !sessionFormatId.equals(that.sessionFormatId) : that.sessionFormatId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardEnrollId != null ? cardEnrollId.hashCode() : 0;
        result = 31 * result + (sessionFormatId != null ? sessionFormatId.hashCode() : 0);
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
    @JoinColumn(name = "session_format_id", referencedColumnName = "session_format_id", nullable = false)
    public SessionFormat getSessionFormatBySessionFormatId() {
        return sessionFormatBySessionFormatId;
    }

    public void setSessionFormatBySessionFormatId(SessionFormat sessionFormatBySessionFormatId) {
        this.sessionFormatBySessionFormatId = sessionFormatBySessionFormatId;
    }
}
