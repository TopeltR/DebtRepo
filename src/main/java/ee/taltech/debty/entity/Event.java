package ee.taltech.debty.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

import ee.taltech.debty.entity.Bill;

@Data
public class Event {
    @Id
    @GeneratedValue
    private Long id;
    private List<User> users;
    private String title;
    private LocalDateTime created;
    private LocalDateTime closed;
    private List<Bill> bills;
}
