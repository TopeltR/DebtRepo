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

        List<Person> contactList = new ArrayList<>();
        contactRepository.findAllByTo(person).forEach(contact -> contactList.add(contact.getFrom()));
        contactRepository.findAllByFrom(person).forEach(contact -> contactList.add(contact.getTo()));

        List<Person> availableContacts = new ArrayList<>();
        userList.forEach(user -> {
            if (!contactList.contains(user)) availableContacts.add(user);
        });
        return availableContacts;
    }

    public void addContact(Long personId1, Long personId2) {
        Optional<Person> personOptional1 = userService.getUserById(personId1);
        Optional<Person> personOptional2 = userService.getUserById(personId2);
        if (personOptional1.isPresent() && personOptional2.isPresent()) {
            Person person1 = personOptional1.get();
            Person person2 = personOptional2.get();
            Contact contact = new Contact();
            contact.setFrom(person1);
            contact.setTo(person2);
            contact.setAccepted(false);
            contactRepository.save(contact);
        }
    }

    public List<Person> getAllContacts(Person person) {
        List<Contact> contacts = contactRepository.findAllByFrom(person);
        contacts.addAll(contactRepository.findAllByTo(person));
        List<Person> contactPersonList = new ArrayList<>();
        contactPersonList.addAll(
                contacts.stream()
                        .filter(contact -> contact.isAccepted() && contact.getFrom() == person)
                        .map(Contact::getTo)
                        .collect(Collectors.toList()));
        contactPersonList.addAll(
                contacts.stream()
                        .filter(contact -> contact.isAccepted() && contact.getTo() == person)
                        .map(Contact::getFrom)
                        .collect(Collectors.toList()));
        return contactPersonList;
    }

    public void acceptContact(Long toId, Long fromId) {
        Optional<Person> toPerson = userService.getUserById(toId);
        Optional<Person> fromPerson = userService.getUserById(fromId);

        if (toPerson.isPresent() && fromPerson.isPresent()) {
            Optional<Contact> contact = contactRepository.findByFromAndTo(toPerson.get(), fromPerson.get());
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

    @Transactional
    public void removeContactById(Long fromId, Long contactId) {
        Optional<Person> fromPerson = userService.getUserById(fromId);
        Optional<Person> toPerson = userService.getUserById(contactId);

        fromPerson.ifPresent(contactRepository::removeContactByFrom);
        fromPerson.ifPresent(contactRepository::removeContactByTo);
        toPerson.ifPresent(contactRepository::removeContactByTo);
        toPerson.ifPresent(contactRepository::removeContactByFrom);

    }
}
