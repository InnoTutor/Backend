/*
MIT License

Copyright (c) 2021 InnoTutor

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package innotutor.innotutor_backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "card_enroll_session_format", schema = "public", catalog = "innotutor")
public class CardEnrollSessionFormat {
    private Long cardEnrollId;
    private Long sessionFormatId;
    private Long cardEnrollSessionFormatId;
    private Card cardByCardEnrollId;
    private SessionFormat sessionFormatBySessionFormatId;

    @Basic
    @Column(name = "card_enroll_id", nullable = false, insertable = false, updatable = false)
    public Long getCardEnrollId() {
        return cardEnrollId;
    }

    public void setCardEnrollId(Long cardEnrollId) {
        this.cardEnrollId = cardEnrollId;
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
    @Column(name = "card_enroll_session_format_id", nullable = false)
    public Long getCardEnrollSessionFormatId() {
        return cardEnrollSessionFormatId;
    }

    public void setCardEnrollSessionFormatId(Long cardEnrollSessionFormatId) {
        this.cardEnrollSessionFormatId = cardEnrollSessionFormatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardEnrollSessionFormat that = (CardEnrollSessionFormat) o;

        if (cardEnrollId != null ? !cardEnrollId.equals(that.cardEnrollId) : that.cardEnrollId != null) return false;
        if (sessionFormatId != null ? !sessionFormatId.equals(that.sessionFormatId) : that.sessionFormatId != null)
            return false;
        if (cardEnrollSessionFormatId != null ? !cardEnrollSessionFormatId.equals(that.cardEnrollSessionFormatId) : that.cardEnrollSessionFormatId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardEnrollId != null ? cardEnrollId.hashCode() : 0;
        result = 31 * result + (sessionFormatId != null ? sessionFormatId.hashCode() : 0);
        result = 31 * result + (cardEnrollSessionFormatId != null ? cardEnrollSessionFormatId.hashCode() : 0);
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
