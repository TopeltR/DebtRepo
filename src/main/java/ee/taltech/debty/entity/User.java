package ee.taltech.debty.entity;

import lombok.Data;

import javax.persistence.Entity;
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

