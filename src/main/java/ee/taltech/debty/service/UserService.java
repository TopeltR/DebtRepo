package ee.taltech.debty.service;

import ee.taltech.debty.entity.Person;
import ee.taltech.debty.model.PersonDto;
import ee.taltech.debty.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<Person> getAllFriendsById(Long id) {
        return personRepository.findById(id).isPresent() ? personRepository.findById(id).get().getFriends()
                : new ArrayList<>();
    }

    public List<Person> addFriendToUserById(Long person, Long friend) {
        Person p = new Person();
        Person f = new Person();
        if (personRepository.findById(person).isPresent()) p = personRepository.findById(person).get();
        if (personRepository.findById(friend).isPresent()) f = personRepository.findById(friend).get();
        p.getFriends().add(f);
        return p.getFriends();
    }

    public boolean emailExists(String email) {
        return personRepository.findByEmail(email) != null;
    }

    public void removeFriendById(Long from_id, Long friend_id) {
        if (personRepository.findById(from_id).isPresent() && personRepository.findById(friend_id).isPresent()) {
            Person person = personRepository.findById(from_id).get();
            Person from = personRepository.findById(friend_id).get();
            person.getFriends().remove(from);
        }

    }
}
