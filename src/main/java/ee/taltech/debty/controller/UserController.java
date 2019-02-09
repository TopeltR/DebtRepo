package ee.taltech.debty.controller;

import ee.taltech.debty.entity.BankAccount;
import ee.taltech.debty.entity.User;
import ee.taltech.debty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    List<User> getUsers() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setName("lmao");
        bankAccount.setNumber("123");
        User user = new User();
        user.setBankAccount(bankAccount);

        return List.of(user);
    }

    @PostMapping("/users/register")
    User registerUser(@Validated @RequestBody User user) {
        return userService.saveUser(user);
    }

}
