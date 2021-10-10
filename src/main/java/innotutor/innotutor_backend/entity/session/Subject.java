package innotutor.innotutor_backend.entity.session;

import innotutor.innotutor_backend.entity.card.Card;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "subject", schema = "public", catalog = "innotutor")
public class Subject {
    private Long subjectId;
    private String name;
    private Timestamp creationDate;
    private Timestamp lastUpdate;
    private Collection<Card> cardsBySubjectId;
    private Collection<Session> sessionsBySubjectId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id", nullable = false)
    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(final Long subjectId) {
        this.subjectId = subjectId;
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
        final Subject subject = (Subject) object;
        if (!Objects.equals(subjectId, subject.subjectId)) {
            return false;
        }
        if (!Objects.equals(name, subject.name)) {
            return false;
        }
        if (!Objects.equals(creationDate, subject.creationDate)) {
            return false;
        }
        return Objects.equals(lastUpdate, subject.lastUpdate);
    }

    @Override
    public int hashCode() {
        int result = subjectId == null ? 0 : subjectId.hashCode();
        result = 31 * result + (name == null ? 0 : name.hashCode());
        result = 31 * result + (creationDate == null ? 0 : creationDate.hashCode());
        result = 31 * result + (lastUpdate == null ? 0 : lastUpdate.hashCode());
        return result;
    }

    @OneToMany(mappedBy = "subjectBySubjectId")
    public Collection<Card> getCardsBySubjectId() {
        return cardsBySubjectId;
    }

    public void setCardsBySubjectId(final Collection<Card> cardsBySubjectId) {
        this.cardsBySubjectId = cardsBySubjectId;
    }

    @OneToMany(mappedBy = "subjectBySubjectId")
    public Collection<Session> getSessionsBySubjectId() {
        return sessionsBySubjectId;
    }

    public void setSessionsBySubjectId(final Collection<Session> sessionsBySubjectId) {
        this.sessionsBySubjectId = sessionsBySubjectId;
    }
}
