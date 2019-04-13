package ee.taltech.debty.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Person from;

    @NotNull
    @ManyToOne
    private Person to;

    private boolean isAccepted = false;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt;
}
