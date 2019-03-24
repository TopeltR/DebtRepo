package ee.taltech.debty.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@ToString
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDateTime created;
    private LocalDateTime modifiedAt;
    @Embedded
    private BankAccount bankAccount;
    @OneToMany
    private List<Event> events;

}

