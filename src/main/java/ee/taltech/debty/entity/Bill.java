package ee.taltech.debty.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
    @ManyToOne
    private Person creator;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private BigDecimal sum;
    private Currency currency;
    @ManyToOne
    private Person buyer;
    @ManyToMany
    private List<Person> people;
    @ManyToMany
    private List<BillPayment> billPayments;
}
