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

import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.user.User;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@NoArgsConstructor
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
    private Collection<CardEnrollSessionFormat> cardEnrollSessionFormatsByCardId;
    private Collection<CardEnrollSessionType> cardEnrollSessionTypesByCardId;

    public CardEnroll(final Long cardId, final Long userId, final Long statusId, final Card cardByCardId, final User userByUserId,
                      final EnrollmentStatus enrollmentStatusByStatusId) {
        this.cardId = cardId;
        this.userId = userId;
        this.statusId = statusId;
        this.cardByCardId = cardByCardId;
        this.userByUserId = userByUserId;
        this.enrollmentStatusByStatusId = enrollmentStatusByStatusId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_enroll_id", nullable = false)
    public Long getCardEnrollId() {
        return cardEnrollId;
    }

    public void setCardEnrollId(final Long cardEnrollId) {
        this.cardEnrollId = cardEnrollId;
    }

    @Basic
    @Column(name = "card_id", nullable = false, insertable = false, updatable = false)
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(final Long cardId) {
        this.cardId = cardId;
    }

    @Basic
    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "status_id", nullable = false, insertable = false, updatable = false)
    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(final Long statusId) {
        this.statusId = statusId;
    }

    @Basic
    @CreationTimestamp
    @Column(name = "creation_date", insertable = false, updatable = false)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @UpdateTimestamp
    @Column(name = "last_update", insertable = false)
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(final Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final CardEnroll that = (CardEnroll) object;
        if (cardEnrollId != null ? !cardEnrollId.equals(that.cardEnrollId) : that.cardEnrollId != null) return false;
        if (cardId != null ? !cardId.equals(that.cardId) : that.cardId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (statusId != null ? !statusId.equals(that.statusId) : that.statusId != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        return lastUpdate != null ? lastUpdate.equals(that.lastUpdate) : that.lastUpdate == null;
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

    public void setCardByCardId(final Card cardByCardId) {
        this.cardByCardId = cardByCardId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(final User userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "status_id", nullable = false)
    public EnrollmentStatus getEnrollmentStatusByStatusId() {
        return enrollmentStatusByStatusId;
    }

    public void setEnrollmentStatusByStatusId(final EnrollmentStatus enrollmentStatusByStatusId) {
        this.enrollmentStatusByStatusId = enrollmentStatusByStatusId;
    }

    @OneToMany(mappedBy = "cardEnrollByCardEnrollId")
    public Collection<CardEnrollSessionFormat> getCardEnrollSessionFormatsByCardId() {
        return cardEnrollSessionFormatsByCardId;
    }

    public void setCardEnrollSessionFormatsByCardId(final Collection<CardEnrollSessionFormat> cardEnrollSessionFormatsByCardId) {
        this.cardEnrollSessionFormatsByCardId = cardEnrollSessionFormatsByCardId;
    }

    @OneToMany(mappedBy = "cardEnrollByCardEnrollId")
    public Collection<CardEnrollSessionType> getCardEnrollSessionTypesByCardId() {
        return cardEnrollSessionTypesByCardId;
    }

    public void setCardEnrollSessionTypesByCardId(final Collection<CardEnrollSessionType> cardEnrollSessionTypesByCardId) {
        this.cardEnrollSessionTypesByCardId = cardEnrollSessionTypesByCardId;
    }
}
