package ee.taltech.debty.service;

import ee.taltech.debty.entity.Person;
import ee.taltech.debty.model.PersonDto;
import ee.taltech.debty.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

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

   /* public List<Person> getAllContactsById(Long id) {
        return personRepository.findById(id).isPresent() ? personRepository.findById(id).get().getContacts()
                : new ArrayList<>();
    }

    public List<Person> addContactToUserById(Long person, Long contact) {
        Person p = new Person();
        Person f = new Person();
        if (personRepository.findById(person).isPresent()) p = personRepository.findById(person).get();
        if (personRepository.findById(contact).isPresent()) f = personRepository.findById(contact).get();
        p.getContacts().add(f);
        personRepository.save(p);
        return p.getContacts();
    }



    public void removeContactById(Long from_id, Long contact_id) {
        if (personRepository.findById(from_id).isPresent() && personRepository.findById(contact_id).isPresent()) {
            Person person = personRepository.findById(from_id).get();
            Person from = personRepository.findById(contact_id).get();
            person.getContacts().remove(from);
            personRepository.save(person);
        }
    }

    public List<Person> getAddableContacts(Long id) {
        List<Person> newContacts = new ArrayList<>();
        Optional<Person> personOptional = getUserById(id);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            List<Person> friends = person.getContacts();
            List<Person> people = getAllUsers();
            newContacts = people.stream().filter(p -> !friends.contains(p)).collect(Collectors.toList());
            newContacts.remove(person);
        }
        return newContacts;
    } */
}
