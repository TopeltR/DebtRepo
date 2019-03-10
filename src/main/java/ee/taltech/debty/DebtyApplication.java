package ee.taltech.debty;

import ee.taltech.debty.entity.BankAccount;
import ee.taltech.debty.entity.Debt;
import ee.taltech.debty.entity.Event;
import ee.taltech.debty.entity.Person;
import ee.taltech.debty.repository.DebtRepository;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

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

        Person person1 = new Person();
        person1.setFirstName("Liine");
        person1.setLastName("Kasak");
        person1.setEmail("liine@kasak.ee");
        person1.setPassword(passwordEncoder.encode("liine"));


        Person person2 = new Person();
        person2.setFirstName("Rasmus");
        person2.setLastName("Rüngenen");
        person2.setEmail("rasmus@rungenen.ee");
        person2.setPassword(passwordEncoder.encode("rasmus"));

        Person person3 = new Person();
        person3.setFirstName("Heli");
        person3.setLastName("Kopter");
        person3.setPassword(passwordEncoder.encode("heli"));

        Person person4 = new Person();
        person4.setFirstName("Kalle");
        person4.setLastName("Kaalikas");
        person4.setPassword(passwordEncoder.encode("kalle"));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setName("Ingmar Liibert");
        bankAccount.setNumber("EE123456789123");

        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setName("Liine Kasak");
        bankAccount1.setNumber("EE123456789123");

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setName("Rasmus Rüngenen");
        bankAccount2.setNumber("EE123456789123");

        person.setBankAccount(bankAccount);
        person1.setBankAccount(bankAccount1);
        person2.setBankAccount(bankAccount2);

        person2.setFriends(new ArrayList<>(Arrays.asList(person1, person, person3)));

        personRepository.save(person);
        personRepository.save(person1);
        personRepository.save(person2);
        personRepository.save(person3);
        personRepository.save(person4);

        Event event = new Event();
        event.setTitle("Reede õhtune kino");
        event.setDescription("Reede õhtune kinoseansi kogu arveldus. Teeme nii, et igaüks paneb siia oma maksumused kirja :)");
        event.setOwner(person);
        event.setPeople(new ArrayList<>(Arrays.asList(person,person1,person2)));

        Event event1 = new Event();
        event1.setTitle("Kardiga rallimine");
        event1.setDescription("Saame kord kuus kokku ning vaatame, kes kõige kiirem on rajal. Teeme standardiks Laagri" +
                "kardiraja, seal hea mõnus sõita. Mul seal käpp ka sees");
        event1.setOwner(person2);
        event1.setPeople(new ArrayList<>(Arrays.asList(person,person1,person2, person3,person4)));

        Event event2 = new Event();
        event2.setTitle("Lauamänguõhtu");
        event2.setDescription("Vaatame jooksvalt, kes mida toob aga põhimõtteliselt siin saame mugavalt järge pidada ostudel");
        event2.setOwner(person1);
        event2.setPeople(new ArrayList<>(Arrays.asList(person,person1,person2,person4)));

        eventRepository.save(event);
        eventRepository.save(event1);
        eventRepository.save(event2);

        Debt debt = new Debt();
        debt.setPayer(person2);
        debt.setReceiver(person);
        debt.setSum(new BigDecimal(10));

        System.out.println("list: " + person1.getFriends());
    }
}

