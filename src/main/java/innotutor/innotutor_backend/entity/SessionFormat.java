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
package innotutor.innotutor_backend.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

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
    @Column(name = "session_format_id", nullable = false)
    public Long getSessionFormatId() {
        return sessionFormatId;
    }

    public void setSessionFormatId(Long sessionFormatId) {
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
