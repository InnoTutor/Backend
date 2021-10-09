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

import innotutor.innotutor_backend.entity.card.CardRating;
import innotutor.innotutor_backend.entity.card.enrollment.CardEnroll;
import innotutor.innotutor_backend.entity.session.Session;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@NoArgsConstructor
@Entity
@Table(name = "user", schema = "public", catalog = "innotutor")
public class User {
    private Long userId;
    private String name;
    private String surname;
    private String email;
    private String picture;
    private String password;
    private String contacts;
    private String description;
    private Timestamp creationDate;
    private Timestamp lastUpdate;
    private Collection<CardEnroll> cardEnrollsByUserId;
    private Collection<CardRating> cardRatingsByUserId;
    private Collection<Request> requestsByUserId;
    private Collection<Service> servicesByUserId;
    private Collection<Session> sessionsByUserId;
    private Collection<SessionStudent> sessionStudentsByUserId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }


    @Basic
    @Column(name = "name", nullable = false, length = 64)
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname", nullable = false, length = 64)
    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 64)
    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }


    @Basic
    @Column(name = "picture", nullable = false, length = 512)
    public String getPicture() {
        return picture;
    }

    public void setPicture(final String picture) {
        this.picture = picture;
    }

    @Basic
    @Column(name = "password", length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "contacts", length = 256)
    public String getContacts() {
        return contacts;
    }

    public void setContacts(final String contacts) {
        this.contacts = contacts;
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
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final User user = (User) object;
        if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (contacts != null ? !contacts.equals(user.contacts) : user.contacts != null) return false;
        if (description != null ? !description.equals(user.description) : user.description != null) return false;
        if (creationDate != null ? !creationDate.equals(user.creationDate) : user.creationDate != null) return false;
        return lastUpdate != null ? lastUpdate.equals(user.lastUpdate) : user.lastUpdate == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<CardEnroll> getCardEnrollsByUserId() {
        return cardEnrollsByUserId;
    }

    public void setCardEnrollsByUserId(final Collection<CardEnroll> cardEnrollsByUserId) {
        this.cardEnrollsByUserId = cardEnrollsByUserId;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<CardRating> getCardRatingsByUserId() {
        return cardRatingsByUserId;
    }

    public void setCardRatingsByUserId(final Collection<CardRating> cardRatingsByUserId) {
        this.cardRatingsByUserId = cardRatingsByUserId;
    }

    @OneToMany(mappedBy = "userByStudentId")
    public Collection<Request> getRequestsByUserId() {
        return requestsByUserId;
    }

    public void setRequestsByUserId(final Collection<Request> requestsByUserId) {
        this.requestsByUserId = requestsByUserId;
    }

    @OneToMany(mappedBy = "userByTutorId")
    public Collection<Service> getServicesByUserId() {
        return servicesByUserId;
    }

    public void setServicesByUserId(final Collection<Service> servicesByUserId) {
        this.servicesByUserId = servicesByUserId;
    }

    @OneToMany(mappedBy = "userByTutorId")
    public Collection<Session> getSessionsByUserId() {
        return sessionsByUserId;
    }

    public void setSessionsByUserId(final Collection<Session> sessionsByUserId) {
        this.sessionsByUserId = sessionsByUserId;
    }

    @OneToMany(mappedBy = "userByStudentId")
    public Collection<SessionStudent> getSessionStudentsByUserId() {
        return sessionStudentsByUserId;
    }

    public void setSessionStudentsByUserId(final Collection<SessionStudent> sessionStudentsByUserId) {
        this.sessionStudentsByUserId = sessionStudentsByUserId;
    }
}
