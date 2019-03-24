package ee.taltech.debty.service;

import ee.taltech.debty.entity.Person;
import ee.taltech.debty.repository.ContactRepository;
import ee.taltech.debty.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactsServiceTest {
    @Mock
    private ContactRepository contactRepository;
    @Mock
    private ContactService contactService;
    @InjectMocks
    private UserService userService;
    @Mock
    private PersonRepository personRepository;

    @Before
    public void generateUsers() {
        Person person = Person.builder().firstName("Ingmar").lastName("Liibert").email("ingmar@liibert").build();
        Person person1 = Person.builder().firstName("Liine").lastName("Kasak").email("liine@kasak.ee").build();
        Person person2 = Person.builder().firstName("Rasmus").lastName("Rüngenen").email("rasmus@rungenen.ee").build();
        when(personRepository.findAll()).thenReturn(Arrays.asList(person,person1, person2));
    }

    @Test
    public void getAllAvailableContacts_shouldReturnAllContacts() {
        Person person2 = Person.builder().firstName("Rasmus").lastName("Rüngenen").email("rasmus@rungenen.ee").build();
        Person person = Person.builder().firstName("Ingmar").lastName("Liibert").email("ingmar@liibert").build();
        Person person1 = Person.builder().firstName("Liine").lastName("Kasak").email("liine@kasak.ee").build();
        when(contactRepository.findAllByTo(person)).thenReturn(Collections.emptyList());
        when(contactRepository.findAllByFrom(person)).thenReturn(Collections.emptyList());

        assertEquals(contactService.getAllAvailableContacts(person), Arrays.asList(person1,person2));

    }
}
