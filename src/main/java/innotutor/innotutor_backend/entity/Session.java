package innotutor.innotutor_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer sessionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User tutor;

    @Column(insertable = false, updatable = false)
    private Integer tutorId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private Set<User> studentList = new HashSet<>();

    @Column
    private String subject;

    @Column
    private String date;

    @Column
    private String startTime;

    @Column
    private String endTime;

    // offline/online
    @Column
    private String formatSession;

    // private/group
    @Column
    private String formatType;

    @Column
    private String description;

    @CreationTimestamp
    @Column(name = "creationDate", insertable = false, updatable = false)
    private Date creationdate;

    @UpdateTimestamp
    @Column(name = "lastUpdate", insertable = false)
    private Date lastupdate;
}
