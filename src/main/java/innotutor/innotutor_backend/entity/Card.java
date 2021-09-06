package innotutor.innotutor_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
abstract public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer cardId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User creator;

    @Column(insertable = false, updatable = false)
    private Integer creatorId;

    @Column
    private String subject;

    @Column
    private String formatSession;

    @Column
    private String formatType;

    @Column
    private String description;

    @CreationTimestamp
    @Column(name = "creationDate", insertable = false, updatable = false)
    private Date creationDate;

    @UpdateTimestamp
    @Column(name = "lastUpdate", insertable = false)
    private Date lastUpdate;
}
