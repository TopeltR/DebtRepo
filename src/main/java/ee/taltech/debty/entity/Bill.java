package ee.taltech.debty.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    @ManyToOne
    private Person creator;
    @NotNull
    private BigDecimal sum;
    @NotNull
    private Currency currency = Currency.getInstance("EUR");
    @NotNull
    @ManyToOne
    private Person buyer;

    private String description;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt;

    @NotNull
    @ManyToMany
    private List<Person> people;
    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    private List<BillPayment> billPayments;
}
