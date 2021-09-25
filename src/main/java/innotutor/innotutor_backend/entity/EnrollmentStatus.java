package innotutor.innotutor_backend.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "enrollment_status", schema = "public", catalog = "innotutor")
public class EnrollmentStatus {
    private Long statusId;
    private String status;
    private Timestamp creationDate;
    private Timestamp lastUpdate;
    private Collection<CardEnroll> cardEnrollsByStatusId;

    @Id
    @Column(name = "status_id", nullable = false)
    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    @Basic
    @Column(name = "status", nullable = false, length = 64)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

        EnrollmentStatus that = (EnrollmentStatus) o;

        if (statusId != null ? !statusId.equals(that.statusId) : that.statusId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (lastUpdate != null ? !lastUpdate.equals(that.lastUpdate) : that.lastUpdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = statusId != null ? statusId.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "enrollmentStatusByStatusId")
    public Collection<CardEnroll> getCardEnrollsByStatusId() {
        return cardEnrollsByStatusId;
    }

    public void setCardEnrollsByStatusId(Collection<CardEnroll> cardEnrollsByStatusId) {
        this.cardEnrollsByStatusId = cardEnrollsByStatusId;
    }
}
