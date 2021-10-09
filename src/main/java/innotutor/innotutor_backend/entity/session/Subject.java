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
