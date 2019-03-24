package ee.taltech.debty.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillPayment {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Person person;
    private BigDecimal sum;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
