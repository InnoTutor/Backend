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

import innotutor.innotutor_backend.entity.card.CardSessionFormat;
import innotutor.innotutor_backend.entity.card.enrollment.CardEnrollSessionFormat;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "session_format", schema = "public", catalog = "innotutor")
public class SessionFormat {
    private Long sessionFormatId;
    private String name;
    private Timestamp creationDate;
    private Timestamp lastUpdate;
    private Collection<CardEnrollSessionFormat> cardEnrollSessionFormatsBySessionFormatId;
    private Collection<CardSessionFormat> cardSessionFormatsBySessionFormatId;
    private Collection<Session> sessionsBySessionFormatId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_format_id", nullable = false)
    public Long getSessionFormatId() {
        return sessionFormatId;
    }

    public void setSessionFormatId(final Long sessionFormatId) {
        this.sessionFormatId = sessionFormatId;
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
        final SessionFormat that = (SessionFormat) object;
        if (!Objects.equals(sessionFormatId, that.sessionFormatId)) {
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
        int result = sessionFormatId == null ? 0 : sessionFormatId.hashCode();
        result = 31 * result + (name == null ? 0 : name.hashCode());
        result = 31 * result + (creationDate == null ? 0 : creationDate.hashCode());
        result = 31 * result + (lastUpdate == null ? 0 : lastUpdate.hashCode());
        return result;
    }

    @OneToMany(mappedBy = "sessionFormatBySessionFormatId")
    public Collection<CardEnrollSessionFormat> getCardEnrollSessionFormatsBySessionFormatId() {
        return cardEnrollSessionFormatsBySessionFormatId;
    }

    public void setCardEnrollSessionFormatsBySessionFormatId(final Collection<CardEnrollSessionFormat> cardEnrollSessionFormatsBySessionFormatId) {
        this.cardEnrollSessionFormatsBySessionFormatId = cardEnrollSessionFormatsBySessionFormatId;
    }

    @OneToMany(mappedBy = "sessionFormatBySessionFormatId")
    public Collection<CardSessionFormat> getCardSessionFormatsBySessionFormatId() {
        return cardSessionFormatsBySessionFormatId;
    }

    public void setCardSessionFormatsBySessionFormatId(final Collection<CardSessionFormat> cardSessionFormatsBySessionFormatId) {
        this.cardSessionFormatsBySessionFormatId = cardSessionFormatsBySessionFormatId;
    }

    @OneToMany(mappedBy = "sessionFormatBySessionFormatId")
    public Collection<Session> getSessionsBySessionFormatId() {
        return sessionsBySessionFormatId;
    }

    public void setSessionsBySessionFormatId(final Collection<Session> sessionsBySessionFormatId) {
        this.sessionsBySessionFormatId = sessionsBySessionFormatId;
    }
}
