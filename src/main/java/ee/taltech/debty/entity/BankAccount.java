package ee.taltech.debty.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Embeddable
public class BankAccount {
    private String number;
    private String name;
    private LocalDateTime created;
    private LocalDateTime modified;
}
