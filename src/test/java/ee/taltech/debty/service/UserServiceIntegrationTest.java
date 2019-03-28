package ee.taltech.debty.service;

import ee.taltech.debty.entity.Person;
import ee.taltech.debty.repository.PersonRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIntegrationTest {

    private final String email = "john@doe.ee";
    @Autowired
    private UserService userService;
    @Autowired
    private PersonRepository personRepository;
    private Person person = Person.builder().firstName("john").lastName("doe").email(email).build();

    @After
    public void cleanup() {
        personRepository.deleteById(person.getId());
    }

    @Test
    public void getAllUsers_withSaveOnePerson_shouldContainSamePerson() {
        person = personRepository.save(person);

        List<Person> allPeople = userService.getAllUsers();

        List<Person> person1 = allPeople.stream()
                .filter(p -> person.getEmail() != null && person.getEmail().equals(p.getEmail())).collect(Collectors.toList());
        assertEquals(person1.size(), 1);
        assertEquals(person1.get(0).getEmail(), person.getEmail());
    }

    @Test
    public void getUserByEmail_withSaveOnePerson_shouldReturnPerson() {
        personRepository.save(person);

        Person person1 = userService.getUserByEmail(email);

        assertEquals(person.getEmail(), person1.getEmail());
    }
}
