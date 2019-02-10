package ee.taltech.debty.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Currency;

@Data
@Entity
public class Debt {
    @Id
    @GeneratedValue
    Long id;
    BigDecimal sum;
    Currency currency;
    @ManyToOne
    User payer;
    @ManyToOne
    User receiver;
}
