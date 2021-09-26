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
import java.sql.Timestamp;

@Entity
@Table(name = "card_enroll", schema = "public", catalog = "innotutor")
public class CardEnroll {
    private Long cardEnrollId;
    private Long cardId;
    private Long userId;
    private Long statusId;
    private Timestamp creationDate;
    private Timestamp lastUpdate;
    private Card cardByCardId;
    private User userByUserId;
    private EnrollmentStatus enrollmentStatusByStatusId;

    @Id
    @Column(name = "card_enroll_id", nullable = false)
    public Long getCardEnrollId() {
        return cardEnrollId;
    }

    public void setCardEnrollId(Long cardEnrollId) {
        this.cardEnrollId = cardEnrollId;
    }

    @Basic
    @Column(name = "card_id", nullable = false, insertable = false, updatable = false)
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    @Basic
    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "status_id", nullable = false, insertable = false, updatable = false)
    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    @Basic
    @Column(name = "creation_date", nullable = true)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "last_update", nullable = true)
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardEnroll that = (CardEnroll) o;

        if (cardEnrollId != null ? !cardEnrollId.equals(that.cardEnrollId) : that.cardEnrollId != null) return false;
        if (cardId != null ? !cardId.equals(that.cardId) : that.cardId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (statusId != null ? !statusId.equals(that.statusId) : that.statusId != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (lastUpdate != null ? !lastUpdate.equals(that.lastUpdate) : that.lastUpdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardEnrollId != null ? cardEnrollId.hashCode() : 0;
        result = 31 * result + (cardId != null ? cardId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
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
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "status_id", nullable = false)
    public EnrollmentStatus getEnrollmentStatusByStatusId() {
        return enrollmentStatusByStatusId;
    }

    public void setEnrollmentStatusByStatusId(EnrollmentStatus enrollmentStatusByStatusId) {
        this.enrollmentStatusByStatusId = enrollmentStatusByStatusId;
    }
}
