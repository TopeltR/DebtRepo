package ee.taltech.debty.service;

import ee.taltech.debty.entity.BankAccount;
import ee.taltech.debty.entity.Person;
import ee.taltech.debty.model.PersonDto;
import ee.taltech.debty.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;

    public Person saveNewUser(PersonDto personDto) {
        Person person = new Person();
        person.setEmail(personDto.getEmail());
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setPassword(passwordEncoder.encode(personDto.getPasswordConfirm()));
        personRepository.save(person);
        return person;
    }

    public Person getUserByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public List<Person> getAllUsers() {
        return personRepository.findAll();
    }

    public Optional<Person> getUserById(Long id) {
        return personRepository.findById(id);
    }

    public boolean emailExists(String email) {
        return personRepository.findByEmail(email) != null;
    }

    public void addBankAccountForUser(BankAccount bankAccount, Long personId) {

        Person person = personRepository.findById(personId).orElseGet(Person::new);
        if (!person.getId().equals(personId)) return;

        bankAccount.setCreatedAt(LocalDateTime.now());
        bankAccount.setModified(LocalDateTime.now());

        person.setModifiedAt(LocalDateTime.now());
        person.setBankAccount(bankAccount);
        personRepository.save(person);
    }
}
