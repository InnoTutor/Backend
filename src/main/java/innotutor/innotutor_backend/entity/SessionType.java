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
        return lastUpdate != null ? lastUpdate.equals(that.lastUpdate) : that.lastUpdate == null;
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
