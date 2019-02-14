package ee.taltech.debty.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class BankAccount {
    @Id
    @GeneratedValue
    private Long id;
    private String number;
    private String name;
    private LocalDateTime created;
    private LocalDateTime modified;
}
