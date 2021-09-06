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
@Table(name = "cvCard")
public class CvCard extends Card {
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private Set<User> newStudents = new HashSet<>();

    @ManyToMany()
    @JoinColumn(name = "userId")
    private Set<User> acceptedStudents = new HashSet<>();
}
