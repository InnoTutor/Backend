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

import innotutor.innotutor_backend.entity.session.SessionFormat;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
        if (cardEnrollId != null ? !cardEnrollId.equals(that.cardEnrollId) : that.cardEnrollId != null) {
            return false;
        }
        if (sessionFormatId != null ? !sessionFormatId.equals(that.sessionFormatId) : that.sessionFormatId != null) {
            return false;
        }
        return cardEnrollSessionFormatId != null ? cardEnrollSessionFormatId.equals(that.cardEnrollSessionFormatId) : that.cardEnrollSessionFormatId == null;
    }

    @Override
    public int hashCode() {
        int result = cardEnrollId != null ? cardEnrollId.hashCode() : 0;
        result = 31 * result + (sessionFormatId != null ? sessionFormatId.hashCode() : 0);
        result = 31 * result + (cardEnrollSessionFormatId != null ? cardEnrollSessionFormatId.hashCode() : 0);
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
