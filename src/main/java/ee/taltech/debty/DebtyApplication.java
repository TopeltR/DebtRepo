package ee.taltech.debty;

import ee.taltech.debty.entity.*;
import ee.taltech.debty.repository.BillRepository;
import ee.taltech.debty.repository.DebtRepository;
import ee.taltech.debty.repository.EventRepository;
import ee.taltech.debty.repository.PersonRepository;
import ee.taltech.debty.service.BillService;
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
import java.util.Currency;
import java.util.List;

@EnableJpaRepositories
@SpringBootApplication
@RequiredArgsConstructor
public class DebtyApplication {

    private final PersonRepository personRepository;
    private final EventRepository eventRepository;
    private final DebtRepository debtRepository;
    private final BillRepository billRepository;
    private final PasswordEncoder passwordEncoder;
    private final BillService billService;

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
        person4.setEmail("kalle@kaalikas.ee");
        person4.setPassword(passwordEncoder.encode("kalle"));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setName("Ingmar Liibert");
        bankAccount.setNumber("EE123456789123");

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setName("Rasmus Rüngenen");
        bankAccount2.setNumber("EE123456789123");

        person.setBankAccount(bankAccount);
        person2.setBankAccount(bankAccount2);


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

        Bill bill1 = new Bill();
        bill1.setTitle("piletid");
        bill1.setBuyer(person1);
        bill1.setCreator(person);
        bill1.setPeople(Arrays.asList(person, person1));
        bill1.setSum(new BigDecimal(11.50));
        bill1.setCreatedAt(LocalDateTime.now());
        List<BillPayment> billPayments1 = new ArrayList<>();
        BillPayment billPayment1;
        BigDecimal sum1 = bill1.getSum().divide(new BigDecimal(bill1.getPeople().size()), 2, RoundingMode.HALF_EVEN);
        for (Person personX : bill1.getPeople()) {
            billPayment1 = new BillPayment();
            billPayment1.setPerson(personX);
            billPayment1.setSum(sum1);
            billPayments1.add(billPayment1);
        }
        bill1.setBillPayments(billPayments1);

        Bill bill2 = new Bill();
        bill2.setTitle("piletid");
        bill2.setBuyer(person);
        bill2.setCreator(person);
        bill2.setPeople(Arrays.asList(person, person1, person2));
        bill2.setSum(new BigDecimal(15.90));
        bill2.setCreatedAt(LocalDateTime.now());
        List<BillPayment> billPayments2 = new ArrayList<>();
        BillPayment billPayment2;
        BigDecimal sum2 = bill2.getSum().divide(new BigDecimal(bill2.getPeople().size()), 2, RoundingMode.HALF_EVEN);

        for (Person personX : bill2.getPeople()) {
            billPayment2 = new BillPayment();
            billPayment2.setPerson(personX);
            billPayment2.setSum(sum2);
            billPayments2.add(billPayment2);
        }
        bill2.setBillPayments(billPayments2);

        event.setBills(Arrays.asList(bill1, bill2));

        Event event1 = new Event();
        event1.setTitle("Kardiga rallimine");
        event1.setDescription("Saame kord kuus kokku ning vaatame, kes kõige kiirem on rajal. Teeme standardiks Laagri" +
                "kardiraja, seal hea mõnus sõita. Mul seal käpp ka sees");
        event1.setOwner(person2);
        event1.setPeople(new ArrayList<>(Arrays.asList(person,person1,person2, person3)));

        Event event2 = new Event();
        event2.setTitle("Lauamänguõhtu");
        event2.setDescription("Vaatame jooksvalt, kes mida toob aga põhimõtteliselt siin saame mugavalt järge pidada ostudel");
        event2.setOwner(person1);
        event2.setPeople(new ArrayList<>(Arrays.asList(person,person1,person2)));

        Event save = eventRepository.save(event);
        eventRepository.save(event1);
        eventRepository.save(event2);

        Debt debt = Debt.builder()
                .title("MIKS?")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.of(2006,9,7,15,42))
                .payer(person2)
                .receiver(person)
                .currency(Currency.getInstance("EUR"))
                .sum(new BigDecimal(10)).build();
        Debt debt1 = Debt.builder()
                .title("TESTIKENE")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.of(1997,9,7,15,42))
                .payer(person1)
                .receiver(person2)
                .currency(Currency.getInstance("EUR"))
                .sum(new BigDecimal(10)).build();
        Debt debt2 = Debt.builder()
                .title("TÄSTIKENE")
                .payer(person)
                .modifiedAt(LocalDateTime.of(2019,9,7,21,42))
                .receiver(person2)
                .currency(Currency.getInstance("EUR"))
                .sum(new BigDecimal(100)).build();
        Debt debt3 = Debt.builder()
                .payer(person)
                .title("ayo")
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.of(2019,9,7,21,43))
                .receiver(person3)
                .currency(Currency.getInstance("EUR"))
                .sum(new BigDecimal(100)).build();

        debtRepository.save(debt);
        debtRepository.save(debt1);
        debtRepository.save(debt2);
        debtRepository.save(debt3);

    }
}

