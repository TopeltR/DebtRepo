package ee.taltech.debty.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private BankAccount bankAccount;
}

