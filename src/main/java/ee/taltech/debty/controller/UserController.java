package ee.taltech.debty.controller;

import ee.taltech.debty.entity.User;
import ee.taltech.debty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id).orElseGet(User::new);
    }

    @PostMapping("/users/register")
    public User registerUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

}
