package ee.taltech.debty.service;

import ee.taltech.debty.entity.Contact;
import ee.taltech.debty.entity.Person;
import ee.taltech.debty.repository.ContactRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContactsServiceTest {
    @Mock
    private ContactRepository contactRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private ContactService contactService;

    private Person person1 = Person.builder().firstName("Ingmar").lastName("Liibert").email("ingmar@liibert").id(1L).build();
    private Person person2 = Person.builder().firstName("Liine").lastName("Kasak").email("liine@kasak.ee").id(2L).build();
    private Person person3 = Person.builder().firstName("Rasmus").lastName("Rüngenen").email("rasmus@rungenen.ee").id(3L).build();

    List<Person> persons = Arrays.asList(person1, person2, person3);

    @Test
    public void getAllAvailableContacts_shouldReturnAllContacts() {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(person2, person3));
        when(contactRepository.findAllByTo(person1)).thenReturn(Collections.emptyList());
        when(contactRepository.findAllByFrom(person1)).thenReturn(Collections.emptyList());

        assertEquals(Arrays.asList(person2,person3), contactService.getAllAvailableContacts(person1));
    }

    @Test
    public void addContact_withPeopleExisting_shouldCallContactRepositorySave() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(person1));
        when(userService.getUserById(2L)).thenReturn(Optional.of(person2));

        Contact contact = Contact.builder().from(person1).to(person2).isAccepted(false).build();

        contactService.acceptContact(1L, 2L);

        verify(contactRepository).save(contact);
    }

    @Test
    public void addContact_withPeopleNotExisting_shouldNotCallContactRepositorySave() {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());
        when(userService.getUserById(2L)).thenReturn(Optional.empty());

        Contact contact = Contact.builder().from(person1).to(person2).isAccepted(false).build();

        contactService.acceptContact(1L, 2L);

        verify(contactRepository, never()).save(contact);
    }


    @Test
    public void acceptContact_withExistingContact_shouldCreateAcceptedContact() {
        Contact contact = Contact.builder().from(person1).to(person2).isAccepted(false).build();
        when(userService.getUserById(1L)).thenReturn(Optional.of(person1));
        when(userService.getUserById(2L)).thenReturn(Optional.of(person2));
        when(contactRepository.findByFromAndTo(any(), any())).thenReturn(Optional.of(contact));

        contactService.acceptContactForPersonFromPerson(1L, 2L);

        assertTrue(contact.isAccepted());
        verify(contactRepository).save(contact);
    }

    @Test
    public void acceptContact_withNoExistingContact_shouldNotCreateAcceptedContact() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(person1));
        when(userService.getUserById(2L)).thenReturn(Optional.of(person2));
        when(contactRepository.findByFromAndTo(any(), any())).thenReturn(Optional.empty());

        contactService.acceptContactForPersonFromPerson(1L, 2L);

        verify(contactRepository, never()).save(any());
    }

    @Test
    public void getIncomingRequests_withExistingRequest_shouldReturnList() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(person1));
        Contact contact = Contact.builder().from(person1).to(person2).isAccepted(false).build();

        when(contactRepository.findAllByTo(person1))
                .thenReturn(new ArrayList<>(Collections.singletonList(contact)));
        List<Person> people = contactService.getIncomingRequests(1L);
        assertEquals(1L, people.size());
    }

    @Test
    public void getIncomingRequests_withNoAcceptedRequests_shouldReturnEmptyList() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(person1));
        Contact contact = Contact.builder().from(person1).to(person2).isAccepted(true).build();
        when(contactRepository.findAllByTo(person1))
                .thenReturn(new ArrayList<>(Collections.singletonList(contact)));

        List<Person> people = contactService.getIncomingRequests(1L);

        assertEquals(Collections.emptyList(), people);
    }

    @Test
    public void getIncomingRequests_withNoExistingPerson_shouldReturnEmptyList() {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());
        List<Person> people = contactService.getIncomingRequests(1L);

        assertEquals(Collections.emptyList(), people);
    }

    @Test
    public void removeContactById_withExistingContact_shouldRemoveContact() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(person1));
        when(userService.getUserById(2L)).thenReturn(Optional.of(person2));
        Contact contact = Contact.builder().from(person1).to(person2).isAccepted(true).build();

        contactRepository.save(contact);

        contactService.removeContactById(1L, 2L);

        assertEquals(Collections.emptyList(), contactRepository.findAll());
    }

    @Test
    public void removeContactById_withNoExistingPerson_shouldNotRemoveContact() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(person1));
        when(userService.getUserById(3L)).thenReturn(Optional.empty());
        Contact contact = Contact.builder().from(person1).to(person2).isAccepted(true).build();

        contactRepository.save(contact);

        contactService.removeContactById(1L, 3L);

        verify(contactRepository, never()).removeContactByFrom(any());
        verify(contactRepository, never()).removeContactByTo(any());
    }
}
