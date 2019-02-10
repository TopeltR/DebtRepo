package ee.taltech.debty.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Event {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany
    private List<User> users;
    private String title;
    private LocalDateTime created;
    private LocalDateTime closed;
    @ManyToMany
    private List<Bill> bills;
}
