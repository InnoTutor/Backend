package innotutor.innotutor_backend.entity.card.enrollment;

import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.user.User;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "card_enroll", schema = "public", catalog = "innotutor")
public class CardEnroll {
    private Long cardEnrollId;
    private Long cardId;
    private Long userId;
    private Long statusId;
    private String description;
    private Timestamp creationDate;
    private Timestamp lastUpdate;
    private Card cardByCardId;
    private User userByUserId;
    private EnrollmentStatus enrollmentStatusByStatusId;
    private Collection<CardEnrollSessionFormat> cardEnrollSessionFormatsByCardId;
    private Collection<CardEnrollSessionType> cardEnrollSessionTypesByCardId;

    public CardEnroll(final Long cardId, final Long userId, final Long statusId, final String description,
                      final Card cardByCardId, final User userByUserId,
                      final EnrollmentStatus enrollmentStatusByStatusId) {
        this.cardId = cardId;
        this.userId = userId;
        this.statusId = statusId;
        this.description = description;
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
    @Column(name = "description", length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
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
        final CardEnroll cardEnroll = (CardEnroll) object;
        if (!Objects.equals(cardEnrollId, cardEnroll.cardEnrollId)) {
            return false;
        }
        if (!Objects.equals(cardId, cardEnroll.cardId)) {
            return false;
        }
        if (!Objects.equals(userId, cardEnroll.userId)) {
            return false;
        }
        if (!Objects.equals(statusId, cardEnroll.statusId)) {
            return false;
        }
        if (!Objects.equals(description, cardEnroll.description)) {
            return false;
        }
        if (!Objects.equals(creationDate, cardEnroll.creationDate)) {
            return false;
        }
        return Objects.equals(lastUpdate, cardEnroll.lastUpdate);
    }

    @Override
    public int hashCode() {
        int result = cardEnrollId == null ? 0 : cardEnrollId.hashCode();
        result = 31 * result + (cardId == null ? 0 : cardId.hashCode());
        result = 31 * result + (userId == null ? 0 : userId.hashCode());
        result = 31 * result + (statusId == null ? 0 : statusId.hashCode());
        result = 31 * result + (description == null ? 0 : description.hashCode());
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

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "status_id", nullable = false)
    public EnrollmentStatus getEnrollmentStatusByStatusId() {
        return enrollmentStatusByStatusId;
    }

    public void setEnrollmentStatusByStatusId(final EnrollmentStatus enrollmentStatusByStatusId) {
        this.enrollmentStatusByStatusId = enrollmentStatusByStatusId;
    }

    @OneToMany(mappedBy = "cardEnrollByCardEnrollId", orphanRemoval = true)
    public Collection<CardEnrollSessionFormat> getCardEnrollSessionFormatsByCardId() {
        return cardEnrollSessionFormatsByCardId;
    }

    public void setCardEnrollSessionFormatsByCardId(final Collection<CardEnrollSessionFormat> cardEnrollSessionFormatsByCardId) {
        this.cardEnrollSessionFormatsByCardId = cardEnrollSessionFormatsByCardId;
    }

    @OneToMany(mappedBy = "cardEnrollByCardEnrollId", orphanRemoval = true)
    public Collection<CardEnrollSessionType> getCardEnrollSessionTypesByCardId() {
        return cardEnrollSessionTypesByCardId;
    }

    public void setCardEnrollSessionTypesByCardId(final Collection<CardEnrollSessionType> cardEnrollSessionTypesByCardId) {
        this.cardEnrollSessionTypesByCardId = cardEnrollSessionTypesByCardId;
    }
}
