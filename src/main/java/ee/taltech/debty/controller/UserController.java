package ee.taltech.debty.controller;

import ee.taltech.debty.entity.Person;
import ee.taltech.debty.model.PersonDto;
import ee.taltech.debty.security.service.SecurityService;
import ee.taltech.debty.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("userForm", new Person());

        return "registration";
    }

    @PostMapping("/register")
    public ResponseEntity registration(@Validated @RequestBody PersonDto userForm) {

        if (userService.emailExists(userForm.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Person person = userService.saveNewUser(userForm);

        securityService.autoLogin(userForm.getEmail(), userForm.getPasswordConfirm());

        return ResponseEntity.status(HttpStatus.OK).body(person);
    }

    @GetMapping("/users/all")
    List<Person> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/loggedin")
    Person getLoggedInUser(Principal principal) {
        String email = principal.getName();
        if (!userService.emailExists(email)) return new Person();
        return userService.getUserByEmail(email);
    }

    @GetMapping("/users/{email:[a-zA-Z]+@[a-zA-Z]+.[a-zA-Z]+}")
    Person getUserByEmail(@PathVariable("email") String email) {
        if (!userService.emailExists(email)) return new Person();
        return userService.getUserByEmail(email);
    }

    @GetMapping("/users/{id:[0-9]+}")
    Person getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id).orElseGet(Person::new);
    }


}
