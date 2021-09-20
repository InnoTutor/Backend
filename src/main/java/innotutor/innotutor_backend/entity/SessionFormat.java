package innotutor.innotutor_backend.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "session_format", schema = "public", catalog = "innotutor")
public class SessionFormat {
    private Integer sessionFormatId;
    private String name;
    private String creationDate;
    private String lastUpdate;
    private Collection<CardEnrollSessionFormat> cardEnrollSessionFormatsBySessionFormatId;
    private Collection<CardSessionFormat> cardSessionFormatsBySessionFormatId;
    private Collection<Session> sessionsBySessionFormatId;

    @Id
    @Column(name = "session_format_id", nullable = false)
    public Integer getSessionFormatId() {
        return sessionFormatId;
    }

    public void setSessionFormatId(Integer sessionFormatId) {
        this.sessionFormatId = sessionFormatId;
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

        SessionFormat that = (SessionFormat) o;

        if (sessionFormatId != null ? !sessionFormatId.equals(that.sessionFormatId) : that.sessionFormatId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (lastUpdate != null ? !lastUpdate.equals(that.lastUpdate) : that.lastUpdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sessionFormatId != null ? sessionFormatId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "sessionFormatBySessionFormatId")
    public Collection<CardEnrollSessionFormat> getCardEnrollSessionFormatsBySessionFormatId() {
        return cardEnrollSessionFormatsBySessionFormatId;
    }

    public void setCardEnrollSessionFormatsBySessionFormatId(Collection<CardEnrollSessionFormat> cardEnrollSessionFormatsBySessionFormatId) {
        this.cardEnrollSessionFormatsBySessionFormatId = cardEnrollSessionFormatsBySessionFormatId;
    }

    @OneToMany(mappedBy = "sessionFormatBySessionFormatId")
    public Collection<CardSessionFormat> getCardSessionFormatsBySessionFormatId() {
        return cardSessionFormatsBySessionFormatId;
    }

    public void setCardSessionFormatsBySessionFormatId(Collection<CardSessionFormat> cardSessionFormatsBySessionFormatId) {
        this.cardSessionFormatsBySessionFormatId = cardSessionFormatsBySessionFormatId;
    }

    @OneToMany(mappedBy = "sessionFormatBySessionFormatId")
    public Collection<Session> getSessionsBySessionFormatId() {
        return sessionsBySessionFormatId;
    }

    public void setSessionsBySessionFormatId(Collection<Session> sessionsBySessionFormatId) {
        this.sessionsBySessionFormatId = sessionsBySessionFormatId;
    }
}
