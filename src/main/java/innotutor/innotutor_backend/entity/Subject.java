package innotutor.innotutor_backend.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Subject {
    private Long subjectId;
    private String name;
    private Timestamp creationDate;
    private Timestamp lastUpdate;
    private Collection<Card> cardsBySubjectId;
    private Collection<Session> sessionsBySubjectId;

    @Id
    @Column(name = "subject_id", nullable = false)
    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
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

        Subject subject = (Subject) o;

        if (subjectId != null ? !subjectId.equals(subject.subjectId) : subject.subjectId != null) return false;
        if (name != null ? !name.equals(subject.name) : subject.name != null) return false;
        if (creationDate != null ? !creationDate.equals(subject.creationDate) : subject.creationDate != null)
            return false;
        if (lastUpdate != null ? !lastUpdate.equals(subject.lastUpdate) : subject.lastUpdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = subjectId != null ? subjectId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "subjectBySubjectId")
    public Collection<Card> getCardsBySubjectId() {
        return cardsBySubjectId;
    }

    public void setCardsBySubjectId(Collection<Card> cardsBySubjectId) {
        this.cardsBySubjectId = cardsBySubjectId;
    }

    @OneToMany(mappedBy = "subjectBySubjectId")
    public Collection<Session> getSessionsBySubjectId() {
        return sessionsBySubjectId;
    }

    public void setSessionsBySubjectId(Collection<Session> sessionsBySubjectId) {
        this.sessionsBySubjectId = sessionsBySubjectId;
    }
}
