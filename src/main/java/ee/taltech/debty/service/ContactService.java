package ee.taltech.debty.service;

import ee.taltech.debty.entity.Contact;
import ee.taltech.debty.entity.Person;
import ee.taltech.debty.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        userList.forEach(user -> {if (!contactList.contains(user)) availableContacts.add(user);});
        return availableContacts;
    }

    public void addContact(Long personId1, Long personId2) {
        Optional<Person> personOptional1 = userService.getUserById(personId1);
        Optional<Person> personOptional2 = userService.getUserById(personId2);
        Person person1 = null;
        Person person2 = null;
        if (personOptional1.isPresent()) person1 = personOptional1.get();
        if (personOptional2.isPresent()) person2 = personOptional2.get();
        if (person1 == null || person2 == null) return;
        Contact contact = new Contact();
        contact.setFrom(person1);
        contact.setTo(person2);
        contact.setAccepted(false);
        contactRepository.save(contact);
    }

    public List<Person> getAllContacts(Person person) {
        List<Contact> contacts = contactRepository.findAllByFrom(person);
        System.out.println(contactRepository.findAllByFrom(person));
        return contacts.stream().filter(Contact::isAccepted).map(Contact::getTo).collect(Collectors.toList());
    }

    public void acceptContact(Long toPerson) {
        Optional<Person> person = userService.getUserById(toPerson);
        Optional<Contact> optionalContact = null;
        if (person.isPresent()) {
            optionalContact = contactRepository.findByTo(person.get());
        }
        if (optionalContact.isPresent()) {
            optionalContact.get().setAccepted(true);
            contactRepository.save(optionalContact.get());
            //contactRepository.save(new Contact(optionalContact.get().getFrom(),optionalContact.get().getTo(),true));
        }
    }

    public List<Person> getWaitingList(Long id) {
        Optional<Person> person = userService.getUserById(id);
        List<Contact> contacts;
        if(person.isPresent()) {
           contacts = contactRepository.findAllByFrom(person.get());
           return contacts.stream().filter(i -> !i.isAccepted()).map(Contact::getTo).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
