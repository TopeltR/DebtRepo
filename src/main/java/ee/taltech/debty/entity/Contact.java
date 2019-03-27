package ee.taltech.debty.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Person from;
    @ManyToOne
    private Person to;
    private boolean isAccepted;

    public Contact(Person from, Person to, boolean isAccepted) {
        this.from = from;
        this.to = to;
        this.isAccepted = isAccepted;
    }
}
