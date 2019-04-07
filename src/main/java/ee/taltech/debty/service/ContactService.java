package ee.taltech.debty.service;

import ee.taltech.debty.entity.Contact;
import ee.taltech.debty.entity.Person;
import ee.taltech.debty.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserService userService;

    public List<Person> getAllAvailableContacts(Person person) {
        List<Person> userList = userService.getAllUsers();
        userList.remove(person);

        List<Person> contactList = getContactList(person);
        return getAvailableContacts(userList, contactList);
    }

    private List<Person> getContactList(Person person) {
        List<Person> contactList = new ArrayList<>();
        contactRepository.findAllByTo(person).forEach(contact -> contactList.add(contact.getFrom()));

        contactRepository.findAllByFrom(person).forEach(contact -> contactList.add(contact.getTo()));
        return contactList;
    }

    private List<Person> getAvailableContacts(List<Person> userList, List<Person> contactList) {
        List<Person> availableContacts = new ArrayList<>();
        userList.forEach(user -> {
            if (!contactList.contains(user)) availableContacts.add(user);
        });
        return availableContacts;
    }

    public void acceptContact(Long personId1, Long personId2) {
        Optional<Person> personOptional1 = userService.getUserById(personId1);
        Optional<Person> personOptional2 = userService.getUserById(personId2);
        if (personOptional1.isPresent() && personOptional2.isPresent()) {
            Contact contact = createContact(personOptional1.get(), personOptional2.get());
            contactRepository.save(contact);
        }
    }

    private Contact createContact(Person person1, Person person2) {
        Contact contact = new Contact();
        contact.setFrom(person1);
        contact.setTo(person2);
        contact.setAccepted(false);
        return contact;
    }

    public List<Contact> getAllContacts(Person person) {
        List<Contact> contacts = contactRepository.findAllByFromOrTo(person, person);
        return contacts.stream().filter(Contact::isAccepted).collect(Collectors.toList());

    }

    public void acceptContactForPersonFromPerson(Long toId, Long fromId) {
        Optional<Person> toPerson = userService.getUserById(toId);
        Optional<Person> fromPerson = userService.getUserById(fromId);

        if (toPerson.isPresent() && fromPerson.isPresent()) {
            Optional<Contact> contact = contactRepository.findByFromAndTo(fromPerson.get(), toPerson.get());
            if (contact.isPresent()) {
                contact.get().setAccepted(true);
                contactRepository.save(contact.get());
            }
        }
    }

    public List<Person> getIncomingRequests(Long id) {
        Optional<Person> person = userService.getUserById(id);
        List<Contact> contacts;
        if (person.isPresent()) {
            contacts = contactRepository.findAllByTo(person.get());
            return contacts
                    .stream()
                    .filter(i -> !i.isAccepted())
                    .map(Contact::getFrom)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public List<Person> getOutgoingRequests(Long id) {
        Optional<Person> person = userService.getUserById(id);
        List<Contact> contacts;
        if (person.isPresent()) {
            contacts = contactRepository.findAllByFrom(person.get());
            return contacts
                    .stream()
                    .filter(i -> !i.isAccepted())
                    .map(Contact::getTo)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Transactional
    public void removeContactById(Long fromId, Long toId) {
        Optional<Person> fromPerson = userService.getUserById(fromId);
        Optional<Person> toPerson = userService.getUserById(toId);
        if (fromPerson.isPresent() && toPerson.isPresent()) {
            contactRepository.removeByFromAndTo(fromPerson.get(), toPerson.get());
            contactRepository.removeByFromAndTo(toPerson.get(), fromPerson.get());
        }
    }
}
