package innotutor.innotutor_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requestCard")
public class RequestCard extends Card {
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private Set<User> respondedTutors = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "userId")
    private User tutor;

    @Column
    private Integer tutorId;
}
