package ee.taltech.debty.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class BillPayment {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    private BigDecimal sum;
    private LocalDateTime created;
    private LocalDateTime modified;
}
