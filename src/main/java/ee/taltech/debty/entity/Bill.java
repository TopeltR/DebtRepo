package ee.taltech.debty.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

@Data
@Entity
public class Bill {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private LocalDateTime created;
    private LocalDateTime modified;
    private BigDecimal sum;
    private Currency currency;
    @ManyToOne
    private Person buyer;
    @ManyToMany
    private List<Person> people;
    @ManyToMany
    private List<BillPayment> billPayments;
}
