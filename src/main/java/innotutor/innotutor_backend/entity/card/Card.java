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

import innotutor.innotutor_backend.entity.card.enrollment.CardEnroll;
import innotutor.innotutor_backend.entity.session.Subject;
import innotutor.innotutor_backend.entity.user.Request;
import innotutor.innotutor_backend.entity.user.Service;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "card", schema = "public", catalog = "innotutor")
public class Card {
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

    public Card(Long subjectId, String description, Boolean hidden, Subject subjectBySubjectId) {
        this.subjectId = subjectId;
        this.description = description;
        this.hidden = hidden;
        this.subjectBySubjectId = subjectBySubjectId;
    }

    public Card() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", nullable = false)
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    @Basic
    @Column(name = "subject_id", nullable = false, insertable = false, updatable = false)
    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    @Basic
    @Column(name = "description", length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "hidden", nullable = false)
    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    @Basic
    @Column(name = "creation_date")
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "last_update")
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

        Card card = (Card) o;

        if (cardId != null ? !cardId.equals(card.cardId) : card.cardId != null) return false;
        if (subjectId != null ? !subjectId.equals(card.subjectId) : card.subjectId != null) return false;
        if (description != null ? !description.equals(card.description) : card.description != null) return false;
        if (creationDate != null ? !creationDate.equals(card.creationDate) : card.creationDate != null) return false;
        return lastUpdate != null ? lastUpdate.equals(card.lastUpdate) : card.lastUpdate == null;
    }

    @Override
    public int hashCode() {
        int result = cardId != null ? cardId.hashCode() : 0;
        result = 31 * result + (subjectId != null ? subjectId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id", nullable = false)
    public Subject getSubjectBySubjectId() {
        return subjectBySubjectId;
    }

    public void setSubjectBySubjectId(Subject subjectBySubjectId) {
        this.subjectBySubjectId = subjectBySubjectId;
    }

    @OneToMany(mappedBy = "cardByCardId")
    public Collection<CardEnroll> getCardEnrollsByCardId() {
        return cardEnrollsByCardId;
    }

    public void setCardEnrollsByCardId(Collection<CardEnroll> cardEnrollsByCardId) {
        this.cardEnrollsByCardId = cardEnrollsByCardId;
    }

    @OneToMany(mappedBy = "cardByCardId")
    public Collection<CardRating> getCardRatingsByCardId() {
        return cardRatingsByCardId;
    }

    public void setCardRatingsByCardId(Collection<CardRating> cardRatingsByCardId) {
        this.cardRatingsByCardId = cardRatingsByCardId;
    }

    @OneToMany(mappedBy = "cardByCardId")
    public Collection<CardSessionFormat> getCardSessionFormatsByCardId() {
        return cardSessionFormatsByCardId;
    }

    public void setCardSessionFormatsByCardId(Collection<CardSessionFormat> cardSessionFormatsByCardId) {
        this.cardSessionFormatsByCardId = cardSessionFormatsByCardId;
    }

    @OneToMany(mappedBy = "cardByCardId")
    public Collection<CardSessionType> getCardSessionTypesByCardId() {
        return cardSessionTypesByCardId;
    }

    public void setCardSessionTypesByCardId(Collection<CardSessionType> cardSessionTypesByCardId) {
        this.cardSessionTypesByCardId = cardSessionTypesByCardId;
    }

    @OneToOne(mappedBy = "cardByCardId")
    public Request getRequestByCardId() {
        return requestByCardId;
    }

    public void setRequestByCardId(Request requestByCardId) {
        this.requestByCardId = requestByCardId;
    }

    @OneToOne(mappedBy = "cardByCardId")
    public Service getServiceByCardId() {
        return serviceByCardId;
    }

    public void setServiceByCardId(Service serviceByCardId) {
        this.serviceByCardId = serviceByCardId;
    }
}
