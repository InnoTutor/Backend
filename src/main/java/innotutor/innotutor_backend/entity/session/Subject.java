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
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final Subject subject = (Subject) object;
        if (subjectId != null ? !subjectId.equals(subject.subjectId) : subject.subjectId != null) return false;
        if (name != null ? !name.equals(subject.name) : subject.name != null) return false;
        if (creationDate != null ? !creationDate.equals(subject.creationDate) : subject.creationDate != null)
            return false;
        return lastUpdate != null ? lastUpdate.equals(subject.lastUpdate) : subject.lastUpdate == null;
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
