package ee.taltech.debty.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Currency;

@Data
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
