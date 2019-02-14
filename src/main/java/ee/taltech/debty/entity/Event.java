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
    private List<User> users;
    private String title;
    private LocalDateTime created;
    private LocalDateTime closed;
    private LocalDateTime modified;
    @ManyToMany
    private List<Bill> bills;
}
