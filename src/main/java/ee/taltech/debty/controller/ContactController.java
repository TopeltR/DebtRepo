package ee.taltech.debty.controller;

import ee.taltech.debty.entity.Person;
import ee.taltech.debty.service.ContactService;
import ee.taltech.debty.service.UserService;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
public class ContactController {
    private final ContactService contactService;
    private final UserService userService;

    public ContactController(ContactService contactService, UserService userService) {
        this.contactService = contactService;
        this.userService = userService;
    }

    @GetMapping("/contact/all/{id}")
    public List<Person> getAllAvailableContacts(@PathVariable("id") Long id) {
        Optional<Person> personOptional = userService.getUserById(id);
        Person person = new Person();
        if (personOptional.isPresent()) person = personOptional.get();
        return contactService.getAllAvailableContacts(person);
    }

    @PostMapping("/contact/add/{to_id}/{contact_id}")
    public void addContact(@PathVariable("to_id") Long id, @PathVariable("contact_id") Long contactId) {
        contactService.addContact(id, contactId);
    }

    @DeleteMapping("/contact/remove/{from_id}/{contact_id}")
    public void removeContact(@PathVariable("from_id") Long from_id, @PathVariable("contact_id") Long contact_id) {
        // contactService.removeContactById(from_id, contact_id);
    }

    @GetMapping("/contact/id/{id}")
    public List<Person> getAllUserContacts(@PathVariable("id") Long id) {
        Optional<Person> personOptional = userService.getUserById(id);
        Person person = new Person();
        if (personOptional.isPresent()) person = personOptional.get();
        return contactService.getAllContacts(person);
    }

    @GetMapping("/contact/waiting/{id}")
    public List<Person> getAllWaitingContacts(@PathVariable("id") Long id) {
        return contactService.getWaitingList(id);
    }

    @PostMapping("/contact/accept/{toId}")
    public void acceptContact(@PathVariable("toId") Long toId) {
        contactService.acceptContact(toId);
    }
}

