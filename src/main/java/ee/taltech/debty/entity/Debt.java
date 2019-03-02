package ee.taltech.debty.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
@Entity
public class Debt {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private BigDecimal sum;
    private Currency currency;
    private LocalDateTime created;
    private LocalDateTime modified;
    @ManyToOne
    private Person payer;
    @ManyToOne
    private Person receiver;
}
