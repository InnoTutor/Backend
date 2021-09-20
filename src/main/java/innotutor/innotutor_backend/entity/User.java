package innotutor.innotutor_backend.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class User {
    private Integer userId;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String contacts;
    private String description;
    private String creationDate;
    private String lastUpdate;
    private Collection<CardEnroll> cardEnrollsByUserId;
    private Collection<CardRating> cardRatingsByUserId;
    private Collection<Request> requestsByUserId;
    private Collection<Service> servicesByUserId;
    private Collection<Session> sessionsByUserId;
    private Collection<SessionStudent> sessionStudentsByUserId;

    @Id
    @Column(name = "user_id", nullable = false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname", nullable = false, length = 64)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 64)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "contacts", nullable = true, length = 256)
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "creation_date", nullable = true, length = 256)
    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "last_update", nullable = true, length = 256)
    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (contacts != null ? !contacts.equals(user.contacts) : user.contacts != null) return false;
        if (description != null ? !description.equals(user.description) : user.description != null) return false;
        if (creationDate != null ? !creationDate.equals(user.creationDate) : user.creationDate != null) return false;
        if (lastUpdate != null ? !lastUpdate.equals(user.lastUpdate) : user.lastUpdate != null) return false;

        return true;
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

    public void setCardEnrollsByUserId(Collection<CardEnroll> cardEnrollsByUserId) {
        this.cardEnrollsByUserId = cardEnrollsByUserId;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<CardRating> getCardRatingsByUserId() {
        return cardRatingsByUserId;
    }

    public void setCardRatingsByUserId(Collection<CardRating> cardRatingsByUserId) {
        this.cardRatingsByUserId = cardRatingsByUserId;
    }

    @OneToMany(mappedBy = "userByStudentId")
    public Collection<Request> getRequestsByUserId() {
        return requestsByUserId;
    }

    public void setRequestsByUserId(Collection<Request> requestsByUserId) {
        this.requestsByUserId = requestsByUserId;
    }

    @OneToMany(mappedBy = "userByTutorId")
    public Collection<Service> getServicesByUserId() {
        return servicesByUserId;
    }

    public void setServicesByUserId(Collection<Service> servicesByUserId) {
        this.servicesByUserId = servicesByUserId;
    }

    @OneToMany(mappedBy = "userByTutorId")
    public Collection<Session> getSessionsByUserId() {
        return sessionsByUserId;
    }

    public void setSessionsByUserId(Collection<Session> sessionsByUserId) {
        this.sessionsByUserId = sessionsByUserId;
    }

    @OneToMany(mappedBy = "userByStudentId")
    public Collection<SessionStudent> getSessionStudentsByUserId() {
        return sessionStudentsByUserId;
    }

    public void setSessionStudentsByUserId(Collection<SessionStudent> sessionStudentsByUserId) {
        this.sessionStudentsByUserId = sessionStudentsByUserId;
    }
}
