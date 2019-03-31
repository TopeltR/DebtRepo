package ee.taltech.debty.controller;

import ee.taltech.debty.entity.BankAccount;
import ee.taltech.debty.entity.Event;
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

    @GetMapping("/users/loggedIn")
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

    @PutMapping("/users")
    public Person updateUser(@RequestBody PersonDto user) {
        return userService.updateUser(user);
    }

    @PostMapping("/users/bankAccount/{userId}")
    public void addBankAccountForUser(@PathVariable("userId") Long userId, @RequestBody BankAccount bankAccount) {
        userService.addBankAccountForUser(bankAccount, userId);
    }
}
