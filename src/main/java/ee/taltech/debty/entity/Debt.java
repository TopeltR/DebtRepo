package ee.taltech.debty.entity;

import ee.taltech.debty.model.DebtStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
@Entity
@AllArgsConstructor
@Builder
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private BigDecimal sum;
    @Builder.Default
    private Currency currency = Currency.getInstance("EUR");
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt;
    private LocalDateTime closedAt;
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
