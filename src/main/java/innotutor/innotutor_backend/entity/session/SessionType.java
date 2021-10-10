package innotutor.innotutor_backend.entity.session;

import innotutor.innotutor_backend.entity.card.CardSessionType;
import innotutor.innotutor_backend.entity.card.enrollment.CardEnrollSessionType;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@NoArgsConstructor
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_type_id", nullable = false)
    public Long getSessionTypeId() {
        return sessionTypeId;
    }

    public void setSessionTypeId(final Long sessionTypeId) {
        this.sessionTypeId = sessionTypeId;
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
        final SessionType that = (SessionType) object;
        if (!Objects.equals(sessionTypeId, that.sessionTypeId)) {
            return false;
        }
        if (!Objects.equals(name, that.name)) {
            return false;
        }
        if (!Objects.equals(creationDate, that.creationDate)) {
            return false;
        }
        return Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        int result = sessionTypeId == null ? 0 : sessionTypeId.hashCode();
        result = 31 * result + (name == null ? 0 : name.hashCode());
        result = 31 * result + (creationDate == null ? 0 : creationDate.hashCode());
        result = 31 * result + (lastUpdate == null ? 0 : lastUpdate.hashCode());
        return result;
    }

    @OneToMany(mappedBy = "sessionTypeBySessionTypeId")
    public Collection<CardEnrollSessionType> getCardEnrollSessionTypesBySessionTypeId() {
        return cardEnrollSessionTypesBySessionTypeId;
    }

    public void setCardEnrollSessionTypesBySessionTypeId(final Collection<CardEnrollSessionType> cardEnrollSessionTypesBySessionTypeId) {
        this.cardEnrollSessionTypesBySessionTypeId = cardEnrollSessionTypesBySessionTypeId;
    }

    @OneToMany(mappedBy = "sessionTypeBySessionTypeId")
    public Collection<CardSessionType> getCardSessionTypesBySessionTypeId() {
        return cardSessionTypesBySessionTypeId;
    }

    public void setCardSessionTypesBySessionTypeId(final Collection<CardSessionType> cardSessionTypesBySessionTypeId) {
        this.cardSessionTypesBySessionTypeId = cardSessionTypesBySessionTypeId;
    }

    @OneToMany(mappedBy = "sessionTypeBySessionTypeId")
    public Collection<Session> getSessionsBySessionTypeId() {
        return sessionsBySessionTypeId;
    }

    public void setSessionsBySessionTypeId(final Collection<Session> sessionsBySessionTypeId) {
        this.sessionsBySessionTypeId = sessionsBySessionTypeId;
    }
}
