package ee.taltech.debty.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
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
