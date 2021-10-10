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
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "user", schema = "public", catalog = "innotutor")
public class User { //NOPMD - suppressed ShortClassName - It has the same database table name.
    // So, we follow such convention
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
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final User user = (User) object;
        if (!Objects.equals(userId, user.userId)) {
            return false;
        }
        if (!Objects.equals(name, user.name)) {
            return false;
        }
        if (!Objects.equals(surname, user.surname)) {
            return false;
        }
        if (!Objects.equals(email, user.email)) {
            return false;
        }
        if (!Objects.equals(password, user.password)) {
            return false;
        }
        if (!Objects.equals(contacts, user.contacts)) {
            return false;
        }
        if (!Objects.equals(description, user.description)) {
            return false;
        }
        if (!Objects.equals(creationDate, user.creationDate)) {
            return false;
        }
        return Objects.equals(lastUpdate, user.lastUpdate);
    }

    @Override
    public int hashCode() {
        int result = userId == null ? 0 : userId.hashCode();
        result = 31 * result + (name == null ? 0 : name.hashCode());
        result = 31 * result + (surname == null ? 0 : surname.hashCode());
        result = 31 * result + (email == null ? 0 : email.hashCode());
        result = 31 * result + (password == null ? 0 : password.hashCode());
        result = 31 * result + (contacts == null ? 0 : contacts.hashCode());
        result = 31 * result + (description == null ? 0 : description.hashCode());
        result = 31 * result + (creationDate == null ? 0 : creationDate.hashCode());
        result = 31 * result + (lastUpdate == null ? 0 : lastUpdate.hashCode());
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
