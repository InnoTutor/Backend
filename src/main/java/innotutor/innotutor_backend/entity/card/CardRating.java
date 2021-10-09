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
package innotutor.innotutor_backend.entity.card;

import innotutor.innotutor_backend.entity.user.User;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "card_rating", schema = "public", catalog = "innotutor")
public class CardRating {
    private Long cardRatingId;
    private Long cardId;
    private Long userId;
    private Integer mark;
    private Timestamp creationDate;
    private Timestamp lastUpdate;
    private Card cardByCardId;
    private User userByUserId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_rating_id", nullable = false)
    public Long getCardRatingId() {
        return cardRatingId;
    }

    public void setCardRatingId(final Long cardRatingId) {
        this.cardRatingId = cardRatingId;
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
    @Column(name = "mark", nullable = false)
    public Integer getMark() {
        return mark;
    }

    public void setMark(final Integer mark) {
        this.mark = mark;
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
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final CardRating that = (CardRating) object;
        if (!Objects.equals(cardRatingId, that.cardRatingId)) {
            return false;
        }
        if (!Objects.equals(cardId, that.cardId)) {
            return false;
        }
        if (!Objects.equals(userId, that.userId)) {
            return false;
        }
        if (!Objects.equals(mark, that.mark)) {
            return false;
        }
        if (!Objects.equals(creationDate, that.creationDate)) {
            return false;
        }
        return Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        int result = cardRatingId == null ? 0 : cardRatingId.hashCode();
        result = 31 * result + (cardId == null ? 0 : cardId.hashCode());
        result = 31 * result + (userId == null ? 0 : userId.hashCode());
        result = 31 * result + (mark == null ? 0 : mark.hashCode());
        result = 31 * result + (creationDate == null ? 0 : creationDate.hashCode());
        result = 31 * result + (lastUpdate == null ? 0 : lastUpdate.hashCode());
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
}
