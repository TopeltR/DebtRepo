package ee.taltech.debty.controller;

import ee.taltech.debty.entity.Person;
import ee.taltech.debty.model.PersonDto;
import ee.taltech.debty.security.service.SecurityService;
import ee.taltech.debty.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import java.security.Principal;
import java.util.List;
import java.util.Set;

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
    public List<Person> getAllUserFriends(@PathVariable("id") Long id) {
        return userService.getAllFriendsById(id);
    }

    @PostMapping("/users/friends/add/{to_id}/{friend_id}")
    public List<Person> addFriend(@PathVariable("to_id") Long id, @PathVariable("friend_id") Long friend_id) {
        return userService.addFriendToUserById(id, friend_id);
    }

    @DeleteMapping("/users/friends/remove/{from_id}/{friend_id}")
    public void removeFriend(@PathVariable("from_id") Long from_id, @PathVariable("friend_id") Long friend_id) {
        userService.removeFriendById(from_id, friend_id);
    }
}
