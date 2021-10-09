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
package innotutor.innotutor_backend.entity.card.enrollment;

import innotutor.innotutor_backend.entity.session.SessionType;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "card_enroll_session_type", schema = "public", catalog = "innotutor")
public class CardEnrollSessionType {
    private Long cardEnrollId;
    private Long sessionTypeId;
    private Long cardEnrollSessionTypeId;
    private CardEnroll cardEnrollByCardEnrollId;
    private SessionType sessionTypeBySessionTypeId;

    public CardEnrollSessionType(final Long cardEnrollId, final Long sessionTypeId, final CardEnroll cardEnrollByCardEnrollId,
                                 final SessionType sessionTypeBySessionTypeId) {
        this.cardEnrollId = cardEnrollId;
        this.sessionTypeId = sessionTypeId;
        this.cardEnrollByCardEnrollId = cardEnrollByCardEnrollId;
        this.sessionTypeBySessionTypeId = sessionTypeBySessionTypeId;
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
    @Column(name = "session_type_id", nullable = false, insertable = false, updatable = false)
    public Long getSessionTypeId() {
        return sessionTypeId;
    }

    public void setSessionTypeId(final Long sessionTypeId) {
        this.sessionTypeId = sessionTypeId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_enroll_session_type_id", nullable = false)
    public Long getCardEnrollSessionTypeId() {
        return cardEnrollSessionTypeId;
    }

    public void setCardEnrollSessionTypeId(final Long cardEnrollSessionTypeId) {
        this.cardEnrollSessionTypeId = cardEnrollSessionTypeId;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final CardEnrollSessionType that = (CardEnrollSessionType) object;
        if (cardEnrollId != null ? !cardEnrollId.equals(that.cardEnrollId) : that.cardEnrollId != null) {
            return false;
        }
        if (sessionTypeId != null ? !sessionTypeId.equals(that.sessionTypeId) : that.sessionTypeId != null) {
            return false;
        }
        return cardEnrollSessionTypeId != null ? cardEnrollSessionTypeId.equals(that.cardEnrollSessionTypeId) : that.cardEnrollSessionTypeId == null;
    }

    @Override
    public int hashCode() {
        int result = cardEnrollId != null ? cardEnrollId.hashCode() : 0;
        result = 31 * result + (sessionTypeId != null ? sessionTypeId.hashCode() : 0);
        result = 31 * result + (cardEnrollSessionTypeId != null ? cardEnrollSessionTypeId.hashCode() : 0);
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
    @JoinColumn(name = "session_type_id", referencedColumnName = "session_type_id", nullable = false)
    public SessionType getSessionTypeBySessionTypeId() {
        return sessionTypeBySessionTypeId;
    }

    public void setSessionTypeBySessionTypeId(final SessionType sessionTypeBySessionTypeId) {
        this.sessionTypeBySessionTypeId = sessionTypeBySessionTypeId;
    }
}
