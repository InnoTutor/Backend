package innotutor.innotutor_backend.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "session_type", schema = "public", catalog = "innotutor")
public class SessionType {
    private Long sessionTypeId;
    private String name;
    private Timestamp creationDate;
    private Timestamp lastUpdate;
    private Collection<CardEnrollSessionType> cardEnrollSessionTypesBySessionTypeId;
    private Collection<CardSessionType> cardSessionTypesBySessionTypeId;
    private Collection<Session> sessionsBySessionTypeId;

    @Id
    @Column(name = "session_type_id", nullable = false)
    public Long getSessionTypeId() {
        return sessionTypeId;
    }

    public void setSessionTypeId(Long sessionTypeId) {
        this.sessionTypeId = sessionTypeId;
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
    @Column(name = "creation_date", nullable = true)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "last_update", nullable = true)
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

        SessionType that = (SessionType) o;

        if (sessionTypeId != null ? !sessionTypeId.equals(that.sessionTypeId) : that.sessionTypeId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (lastUpdate != null ? !lastUpdate.equals(that.lastUpdate) : that.lastUpdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sessionTypeId != null ? sessionTypeId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "sessionTypeBySessionTypeId")
    public Collection<CardEnrollSessionType> getCardEnrollSessionTypesBySessionTypeId() {
        return cardEnrollSessionTypesBySessionTypeId;
    }

    public void setCardEnrollSessionTypesBySessionTypeId(Collection<CardEnrollSessionType> cardEnrollSessionTypesBySessionTypeId) {
        this.cardEnrollSessionTypesBySessionTypeId = cardEnrollSessionTypesBySessionTypeId;
    }

    @OneToMany(mappedBy = "sessionTypeBySessionTypeId")
    public Collection<CardSessionType> getCardSessionTypesBySessionTypeId() {
        return cardSessionTypesBySessionTypeId;
    }

    public void setCardSessionTypesBySessionTypeId(Collection<CardSessionType> cardSessionTypesBySessionTypeId) {
        this.cardSessionTypesBySessionTypeId = cardSessionTypesBySessionTypeId;
    }

    @OneToMany(mappedBy = "sessionTypeBySessionTypeId")
    public Collection<Session> getSessionsBySessionTypeId() {
        return sessionsBySessionTypeId;
    }

    public void setSessionsBySessionTypeId(Collection<Session> sessionsBySessionTypeId) {
        this.sessionsBySessionTypeId = sessionsBySessionTypeId;
    }
}
