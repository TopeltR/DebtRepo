package ee.taltech.debty.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@Data
@Entity
public class Bill {

    @Id
    Long id;
    String title;
    String description;
    User buyer;
    List<User> users;
    BigDecimal sum;
    Currency currency;
    List<BillPayment> billPayments;

}
