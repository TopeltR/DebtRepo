package ee.taltech.debty.service;

import ee.taltech.debty.entity.Person;
import ee.taltech.debty.model.PersonDto;
import ee.taltech.debty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Person saveNewUser(PersonDto personDto) {
        Person person = new Person();
        person.setEmail(personDto.getEmail());
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setPassword(passwordEncoder.encode(personDto.getPasswordConfirm()));
        userRepository.save(person);
        return person;
    }

    public Person getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<Person> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Person> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public boolean checkIfEmailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
