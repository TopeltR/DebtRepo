package ee.taltech.debty;

import ee.taltech.debty.entity.*;
import ee.taltech.debty.model.DebtStatus;
import ee.taltech.debty.repository.DebtRepository;
import ee.taltech.debty.repository.EventRepository;
import ee.taltech.debty.repository.PersonRepository;
import ee.taltech.debty.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableJpaRepositories
@SpringBootApplication
@RequiredArgsConstructor
public class DebtyApplication {

    private final PersonRepository personRepository;
    private final EventRepository eventRepository;
    private final DebtRepository debtRepository;
    private final PasswordEncoder passwordEncoder;
    private final ContactService contactService;

    public static void main(String[] args) {
        SpringApplication.run(DebtyApplication.class, args);
    }
}
