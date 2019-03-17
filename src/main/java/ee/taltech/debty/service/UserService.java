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


}
