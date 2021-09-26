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
public class Service {
    private Long tutorId;
    private Long cardId;
    private Long serviceId;
    private User userByTutorId;
    private Card cardByCardId;

    @Basic
    @Column(name = "tutor_id", nullable = false, insertable = false, updatable = false)
    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
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
    @Column(name = "service_id", nullable = false)
    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service = (Service) o;

        if (tutorId != null ? !tutorId.equals(service.tutorId) : service.tutorId != null) return false;
        if (cardId != null ? !cardId.equals(service.cardId) : service.cardId != null) return false;
        if (serviceId != null ? !serviceId.equals(service.serviceId) : service.serviceId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tutorId != null ? tutorId.hashCode() : 0;
        result = 31 * result + (cardId != null ? cardId.hashCode() : 0);
        result = 31 * result + (serviceId != null ? serviceId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "tutor_id", referencedColumnName = "user_id", nullable = false)
    public User getUserByTutorId() {
        return userByTutorId;
    }

    public void setUserByTutorId(User userByTutorId) {
        this.userByTutorId = userByTutorId;
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
