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
@Table(name = "card_session_type", schema = "public", catalog = "innotutor")
public class CardSessionType {
    private Long cardId;
    private Long sessionTypeId;
    private Long cardSessionTypeId;
    private Card cardByCardId;
    private SessionType sessionTypeBySessionTypeId;

    @Basic
    @Column(name = "card_id", nullable = false, insertable = false, updatable = false)
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    @Basic
    @Column(name = "session_type_id", nullable = false, insertable = false, updatable = false)
    public Long getSessionTypeId() {
        return sessionTypeId;
    }

    public void setSessionTypeId(Long sessionTypeId) {
        this.sessionTypeId = sessionTypeId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_session_type_id", nullable = false)
    public Long getCardSessionTypeId() {
        return cardSessionTypeId;
    }

    public void setCardSessionTypeId(Long cardSessionTypeId) {
        this.cardSessionTypeId = cardSessionTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardSessionType that = (CardSessionType) o;

        if (cardId != null ? !cardId.equals(that.cardId) : that.cardId != null) return false;
        if (sessionTypeId != null ? !sessionTypeId.equals(that.sessionTypeId) : that.sessionTypeId != null)
            return false;
        return cardSessionTypeId != null ? cardSessionTypeId.equals(that.cardSessionTypeId) : that.cardSessionTypeId == null;
    }

    @Override
    public int hashCode() {
        int result = cardId != null ? cardId.hashCode() : 0;
        result = 31 * result + (sessionTypeId != null ? sessionTypeId.hashCode() : 0);
        result = 31 * result + (cardSessionTypeId != null ? cardSessionTypeId.hashCode() : 0);
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
