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
@Table(name = "service", schema = "public", catalog = "innotutor")
public class Service {
    private Long tutorId;
    private Long cardId;
    private Long serviceId;
    private User userByTutorId;
    private Card cardByCardId;

    public Service(final Long tutorId, final Long cardId, final User userByTutorId, final Card cardByCardId) {
        this.tutorId = tutorId;
        this.cardId = cardId;
        this.userByTutorId = userByTutorId;
        this.cardByCardId = cardByCardId;
    }

    @Basic
    @Column(name = "tutor_id", nullable = false, insertable = false, updatable = false)
    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(final Long tutorId) {
        this.tutorId = tutorId;
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
    @Column(name = "service_id", nullable = false)
    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Service service = (Service) object;
        if (!Objects.equals(tutorId, service.tutorId)) {
            return false;
        }
        if (!Objects.equals(cardId, service.cardId)) {
            return false;
        }
        return Objects.equals(serviceId, service.serviceId);
    }

    @Override
    public int hashCode() {
        int result = tutorId == null ? 0 : tutorId.hashCode();
        result = 31 * result + (cardId == null ? 0 : cardId.hashCode());
        result = 31 * result + (serviceId == null ? 0 : serviceId.hashCode());
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "tutor_id", referencedColumnName = "user_id", nullable = false)
    public User getUserByTutorId() {
        return userByTutorId;
    }

    public void setUserByTutorId(final User userByTutorId) {
        this.userByTutorId = userByTutorId;
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
