package ee.taltech.debty.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class BankAccount {
    @Id
    @GeneratedValue
    private Long id;
    private String number;
    private String name;
}
