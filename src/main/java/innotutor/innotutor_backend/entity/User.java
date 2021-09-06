package innotutor.innotutor_backend.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer userId;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String email;

    @Column
    private String contacts;

    @CreationTimestamp
    @Column(name = "creationDate", insertable = false, updatable = false)
    private Date creationdate;

    @UpdateTimestamp
    @Column(name = "lastupDate", insertable = false)
    private Date lastupdate;
}
