package ee.taltech.debty.controller;

import ee.taltech.debty.entity.Person;
import ee.taltech.debty.model.PersonDto;
import ee.taltech.debty.security.service.SecurityService;
import ee.taltech.debty.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;

    @PostMapping("/register")
    public ResponseEntity register(@Validated @RequestBody PersonDto userForm) {

        if (userService.emailExists(userForm.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Person person = userService.saveNewUser(userForm);

        securityService.autoLogin(userForm.getEmail(), userForm.getPasswordConfirm());

        return ResponseEntity.status(HttpStatus.OK).body(person);
    }

    @GetMapping("/users/all")
    public List<Person> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/loggedInUser")
    public Person getLoggedInUser(Principal principal) {
        String email = principal.getName();
        if (!userService.emailExists(email)) return null;
        return userService.getUserByEmail(email);
    }

    @GetMapping("/users/email/{email}")
    public Person getUserByEmail(@Email @PathVariable("email") String email) {
        if (!userService.emailExists(email)) return new Person();
        return userService.getUserByEmail(email);
    }

    @GetMapping("/users/id/{id}")
    public Person getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id).orElseGet(Person::new);
    }

    @GetMapping("/users/friends/id/{id}")
    public List<Person> getAllUserContacts(@PathVariable("id") Long id) {
        return userService.getAllContactsById(id);
    }

    @PostMapping("/users/friends/add/{to_id}/{contact_id}")
    public List<Person> addContact(@PathVariable("to_id") Long id, @PathVariable("contact_id") Long contact_id) {
        return userService.addContactToUserById(id, contact_id);
    }

    @DeleteMapping("/users/friends/remove/{from_id}/{contact_id}")
    public void removeContact(@PathVariable("from_id") Long from_id, @PathVariable("contact_id") Long contact_id) {
        userService.removeContactById(from_id, contact_id);
    }
}
