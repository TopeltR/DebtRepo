package ee.taltech.debty.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class BankAccount {
    @Id
    private Long id;
    private String number;
    private String name;
}
