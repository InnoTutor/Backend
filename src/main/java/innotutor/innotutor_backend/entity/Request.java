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
public class Request {
    private Long studentId;
    private Long cardId;
    private Long requestId;
    private User userByStudentId;
    private Card cardByCardId;

    @Basic
    @Column(name = "student_id", nullable = false, insertable = false, updatable = false)
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "card_id", nullable = false, insertable = false, updatable = false)
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    @Id
    @Column(name = "request_id", nullable = false)
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (studentId != null ? !studentId.equals(request.studentId) : request.studentId != null) return false;
        if (cardId != null ? !cardId.equals(request.cardId) : request.cardId != null) return false;
        if (requestId != null ? !requestId.equals(request.requestId) : request.requestId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId != null ? studentId.hashCode() : 0;
        result = 31 * result + (cardId != null ? cardId.hashCode() : 0);
        result = 31 * result + (requestId != null ? requestId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_id", nullable = false)
    public User getUserByStudentId() {
        return userByStudentId;
    }

    public void setUserByStudentId(User userByStudentId) {
        this.userByStudentId = userByStudentId;
    }

    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "card_id", nullable = false)
    public Card getCardByCardId() {
        return cardByCardId;
    }

    public void setCardByCardId(Card cardByCardId) {
        this.cardByCardId = cardByCardId;
    }
}
