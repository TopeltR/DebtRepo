package ee.taltech.debty.entity;

import ee.taltech.debty.model.DebtStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@AllArgsConstructor
@Builder
public class Debt {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private BigDecimal sum;
    @Builder.Default
    private Currency currency = Currency.getInstance("EUR");
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt;
    @ManyToOne
    private Person payer;
    @ManyToOne
    private Person receiver;
    @ManyToOne
    private Person owner;
    @Builder.Default
    private DebtStatus status = DebtStatus.NEW;

    public Debt() {
        this.status = DebtStatus.NEW;
        this.createdAt = LocalDateTime.now();
        this.currency = Currency.getInstance("EUR");
    }
}
