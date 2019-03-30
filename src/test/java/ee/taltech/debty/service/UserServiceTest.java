package ee.taltech.debty.service;

import ee.taltech.debty.entity.BankAccount;
import ee.taltech.debty.entity.Person;
import ee.taltech.debty.model.PersonDto;
import ee.taltech.debty.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PasswordEncoder passwordEncoder;



    private Person person = Person.builder().firstName("Bob").lastName("Builder")
            .id(3L).build();
    private Person person2 = Person.builder().firstName("Kalevi").lastName("Poeg").id(4L).build();

    @Test
    public void setParamsFromDto_withEmailChange_shouldChangePersonEmail() {
        PersonDto personDto = new PersonDto();
        personDto.setEmail("bob@builer.com");
        when(personRepository.findByEmail("bob@builer.com")).thenReturn(person);
        userService.setParamsFromDto(person, personDto);
        assertEquals("bob@builer.com", personDto.getEmail());
    }

    @Test
    public void setParamsFromDto_withNameChange_shouldChangePersonName() {
        PersonDto personDto = new PersonDto();
        personDto.setFirstName("Bob");
        personDto.setLastName("Builder");
        userService.setParamsFromDto(person, personDto);
        assertEquals("Bob", personDto.getFirstName());
        assertEquals("Builder", personDto.getLastName());
    }

    @Test
    public void setParamsFromDto_withPasswordChange_shouldChangePersonPassword() {
        PersonDto personDto = new PersonDto();
        assertNull(person.getPassword());
        personDto.setPassword("testing");
        when(passwordEncoder.encode(personDto.getPasswordConfirm())).thenReturn("testing");
        userService.setParamsFromDto(person, personDto);
        assertFalse(person.getPassword().isEmpty());
    }

    @Test
    public void toUser_shouldReturnUserWithParameters() {
        PersonDto personDto = new PersonDto();
        personDto.setFirstName("Bob");
        personDto.setLastName("Builder");
        personDto.setEmail("bob@builer.com");
        Person person = userService.toUser(personDto);
        assertEquals("Bob", person.getFirstName());
        assertNull(person.getPassword());
    }

    @Test
    public void saveNewUser_shouldSaveNewUser() {
        PersonDto personDto = new PersonDto();
        personDto.setFirstName("Bob");
        personDto.setLastName("Builder");
        personDto.setEmail("bob@builer.com");
        Person person = userService.saveNewUser(personDto);
        verify(personRepository).save(person);
    }

    @Test
    public void getUserByEmail_shouldReturnUser() {
        when(personRepository.findByEmail("bob@builer.com")).thenReturn(person);
        userService.getUserByEmail("bob@builer.com");
        verify(personRepository).findByEmail("bob@builer.com");
    }

    @Test
    public void getAllUsers_shouldReturnTwoUsers() {
        when(personRepository.findAll()).thenReturn(Arrays.asList(person,person2));
        assertNotNull(userService.getAllUsers());
        verify(personRepository).findAll();
    }

    @Test
    public void emailExists_withExistingPerson_shouldReturnTrue() {
        when(personRepository.findByEmail("bob@builder.com")).thenReturn(person);
        assertTrue(userService.emailExists("bob@builder.com"));
        verify(personRepository).findByEmail("bob@builder.com");
    }

    @Test
    public void addBankAccountForUser_shouldSaveBankAccountForPerson() {
        when(personRepository.findById(3L)).thenReturn(Optional.ofNullable(person));
        BankAccount bankAccount = new BankAccount();
        bankAccount.setNumber("EE12345612345");
        bankAccount.setName("SEB");

        userService.addBankAccountForUser(bankAccount,3L);
        assertEquals("SEB", person.getBankAccount().getName());
        verify(personRepository).save(person);

    }

    @Test
    public void updateUser_shouldUpdateUsersInformation() {
        PersonDto personDto = new PersonDto();
        personDto.setId(3L);
        personDto.setEmail("bob@builder.com");
        when(personRepository.findById(3L)).thenReturn(Optional.ofNullable(person));
        userService.updateUser(personDto);
        verify(personRepository).save(person);
    }

    @Test
    public void updateUser_withNoUserPresent_shouldReturnNull() {
        PersonDto personDto = new PersonDto();
        personDto.setId(3L);
        personDto.setEmail("bob@builder.com");
        when(personRepository.findById(3L)).thenReturn(Optional.ofNullable(person));
        assertNull(userService.updateUser(personDto));
    }



}
