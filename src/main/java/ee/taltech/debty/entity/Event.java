package ee.taltech.debty.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@ToString
public class Event {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany
    private List<Person> people;
    @ManyToOne
    private Person owner;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime closed;
    private LocalDateTime modified;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Bill> bills;
}
