package ee.taltech.debty.controller;

import ee.taltech.debty.entity.Person;
import ee.taltech.debty.model.PersonDto;
import ee.taltech.debty.security.service.SecurityService;
import ee.taltech.debty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;

    @Autowired
    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("userForm", new Person());

        return "registration";
    }

    @PostMapping("/register")
    public ResponseEntity registration(@Validated @RequestBody PersonDto userForm) {

        if (userService.checkIfEmailExist(userForm.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Person person = userService.saveNewUser(userForm);

        securityService.autologin(userForm.getEmail(), userForm.getPasswordConfirm());

        return ResponseEntity.status(HttpStatus.OK).body(person);
    }

    @GetMapping("/people/all")
    List<Person> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/people/{email:[a-zA-Z]+@[a-zA-Z]+.[a-zA-Z]+}")
    Person getUserByEmail(@PathVariable("email") String email) {
        if (!userService.checkIfEmailExist(email)) return new Person();
        return userService.getUserByEmail(email);
    }

    @GetMapping("/people/{id:[0-9]+}")
    Person getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id).orElseGet(Person::new);
    }

}
