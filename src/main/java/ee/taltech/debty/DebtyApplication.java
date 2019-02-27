package ee.taltech.debty;

import ee.taltech.debty.entity.BankAccount;
import ee.taltech.debty.entity.Event;
import ee.taltech.debty.entity.Person;
import ee.taltech.debty.repository.EventRepository;
import ee.taltech.debty.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableJpaRepositories
@SpringBootApplication
@RequiredArgsConstructor
public class DebtyApplication {
    private final PersonRepository personRepository;
    private final EventRepository eventRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(DebtyApplication.class, args);
    }

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        Person person = new Person();
        person.setFirstName("Ingmar");
        person.setLastName("Liibert");
        person.setEmail("ingmar@liibert.ee");
        String password = passwordEncoder.encode("ingmar");
        person.setPassword(password);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setName("Ingmar Liibert");
        bankAccount.setNumber("EE123456789123");

        personRepository.save(person);

        Person p = new Person();
        p.setFirstName("LALA");
        p.setLastName("LALA");
        p.setEmail("lala@lala.com");
        p.setPassword(password);

        p.setBankAccount(bankAccount);

        personRepository.save(p);

        for (int i = 1; i < 10; i++) {
            Event event = new Event();
            event.setTitle("Event number " + i);
            event.setDescription("this is a descrition.... Doggo ipsum shoob wow very biscit much ruin diet the neighborhood pupper long woofer, pupper long bois such treat. Puggo heck shibe very hand that feed shibe, wow very biscit such treat. Wrinkler fat boi shoober tungg, wrinkler.");
            event.setOwner(p);
            event.setPeople(new ArrayList<>(Arrays.asList(p, person)));
            eventRepository.save(event);
        }

        System.out.println(eventRepository.findAll());
        System.out.println("Password hash: " + password);
    }
}

