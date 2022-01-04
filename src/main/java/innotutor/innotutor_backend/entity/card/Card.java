package innotutor.innotutor_backend.entity.card;

import innotutor.innotutor_backend.entity.card.enrollment.CardEnroll;
import innotutor.innotutor_backend.entity.session.Subject;
import innotutor.innotutor_backend.entity.user.Request;
import innotutor.innotutor_backend.entity.user.Service;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "card", schema = "public", catalog = "innotutor")
public class Card { //NOPMD - suppressed ShortClassName - It has the same database table name.
    private final static String CARD_BY_CARD_ID = "cardByCardId";
    // So, we follow such convention
    private Long cardId;
    private Long subjectId;
    private String description;
    private Boolean hidden;
    private Timestamp creationDate;
    private Timestamp lastUpdate;
    private Subject subjectBySubjectId;
    private Request requestByCardId;
    private Service serviceByCardId;
    private Collection<CardEnroll> cardEnrollsByCardId;
    private Collection<CardRating> cardRatingsByCardId;
    private Collection<CardSessionFormat> cardSessionFormatsByCardId;
    private Collection<CardSessionType> cardSessionTypesByCardId;

    public Card(final Long subjectId, final String description, final Boolean hidden, final Subject subjectBySubjectId) {
        this.subjectId = subjectId;
        this.description = description;
        this.hidden = hidden;
        this.subjectBySubjectId = subjectBySubjectId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", nullable = false)
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(final Long cardId) {
        this.cardId = cardId;
    }

    @Basic
    @Column(name = "subject_id", nullable = false, insertable = false, updatable = false)
    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(final Long subjectId) {
        this.subjectId = subjectId;
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
    @Column(name = "hidden", nullable = false)
    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(final Boolean hidden) {
        this.hidden = hidden;
    }

    @Basic
    @Column(name = "creation_date")
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "last_update")
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
        final Card card = (Card) object;
        if (!Objects.equals(cardId, card.cardId)) {
            return false;
        }
        if (!Objects.equals(subjectId, card.subjectId)) {
            return false;
        }
        if (!Objects.equals(description, card.description)) {
            return false;
        }
        if (!Objects.equals(creationDate, card.creationDate)) {
            return false;
        }
        return Objects.equals(lastUpdate, card.lastUpdate);
    }

    @Override
    public int hashCode() {
        int result = cardId == null ? 0 : cardId.hashCode();
        result = 31 * result + (subjectId == null ? 0 : subjectId.hashCode());
        result = 31 * result + (description == null ? 0 : description.hashCode());
        result = 31 * result + (creationDate == null ? 0 : creationDate.hashCode());
        result = 31 * result + (lastUpdate == null ? 0 : lastUpdate.hashCode());
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id", nullable = false)
    public Subject getSubjectBySubjectId() {
        return subjectBySubjectId;
    }

    public void setSubjectBySubjectId(final Subject subjectBySubjectId) {
        this.subjectBySubjectId = subjectBySubjectId;
    }

    @OneToMany(mappedBy = CARD_BY_CARD_ID, cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Collection<CardEnroll> getCardEnrollsByCardId() {
        return cardEnrollsByCardId;
    }

    public void setCardEnrollsByCardId(final Collection<CardEnroll> cardEnrollsByCardId) {
        this.cardEnrollsByCardId = cardEnrollsByCardId;
    }

    @OneToMany(mappedBy = CARD_BY_CARD_ID)
    public Collection<CardRating> getCardRatingsByCardId() {
        return cardRatingsByCardId;
    }

    public void setCardRatingsByCardId(final Collection<CardRating> cardRatingsByCardId) {
        this.cardRatingsByCardId = cardRatingsByCardId;
    }

    @OneToMany(mappedBy = CARD_BY_CARD_ID)
    public Collection<CardSessionFormat> getCardSessionFormatsByCardId() {
        return cardSessionFormatsByCardId;
    }

    public void setCardSessionFormatsByCardId(final Collection<CardSessionFormat> cardSessionFormatsByCardId) {
        this.cardSessionFormatsByCardId = cardSessionFormatsByCardId;
    }

    @OneToMany(mappedBy = CARD_BY_CARD_ID)
    public Collection<CardSessionType> getCardSessionTypesByCardId() {
        return cardSessionTypesByCardId;
    }

    public void setCardSessionTypesByCardId(final Collection<CardSessionType> cardSessionTypesByCardId) {
        this.cardSessionTypesByCardId = cardSessionTypesByCardId;
    }

    @OneToOne(mappedBy = CARD_BY_CARD_ID)
    public Request getRequestByCardId() {
        return requestByCardId;
    }

    public void setRequestByCardId(final Request requestByCardId) {
        this.requestByCardId = requestByCardId;
    }

    @OneToOne(mappedBy = CARD_BY_CARD_ID)
    public Service getServiceByCardId() {
        return serviceByCardId;
    }

    public void setServiceByCardId(final Service serviceByCardId) {
        this.serviceByCardId = serviceByCardId;
    }
}
