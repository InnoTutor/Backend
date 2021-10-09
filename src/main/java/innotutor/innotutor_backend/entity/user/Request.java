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
package innotutor.innotutor_backend.entity.user;

import innotutor.innotutor_backend.entity.card.Card;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "request", schema = "public", catalog = "innotutor")
public class Request {
    private Long studentId;
    private Long cardId;
    private Long requestId;
    private User userByStudentId;
    private Card cardByCardId;

    public Request(final Long studentId, final Long cardId, final User userByStudentId, final Card cardByCardId) {
        this.studentId = studentId;
        this.cardId = cardId;
        this.userByStudentId = userByStudentId;
        this.cardByCardId = cardByCardId;
    }

    @Basic
    @Column(name = "student_id", nullable = false, insertable = false, updatable = false)
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(final Long studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "card_id", nullable = false, insertable = false, updatable = false)
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(final Long cardId) {
        this.cardId = cardId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", nullable = false)
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(final Long requestId) {
        this.requestId = requestId;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Request request = (Request) object;
        if (!Objects.equals(studentId, request.studentId)) {
            return false;
        }
        if (!Objects.equals(cardId, request.cardId)) {
            return false;
        }
        return Objects.equals(requestId, request.requestId);
    }

    @Override
    public int hashCode() {
        int result = studentId == null ? 0 : studentId.hashCode();
        result = 31 * result + (cardId == null ? 0 : cardId.hashCode());
        result = 31 * result + (requestId == null ? 0 : requestId.hashCode());
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_id", nullable = false)
    public User getUserByStudentId() {
        return userByStudentId;
    }

    public void setUserByStudentId(final User userByStudentId) {
        this.userByStudentId = userByStudentId;
    }

    @OneToOne
    @JoinColumn(name = "card_id", referencedColumnName = "card_id", nullable = false)
    public Card getCardByCardId() {
        return cardByCardId;
    }

    public void setCardByCardId(final Card cardByCardId) {
        this.cardByCardId = cardByCardId;
    }
}
