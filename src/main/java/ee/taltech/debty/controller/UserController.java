package ee.taltech.debty.controller;

import ee.taltech.debty.entity.BankAccount;
import ee.taltech.debty.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @GetMapping("/users")
    List<User> getUsers() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setName("lmao");
        bankAccount.setNumber("123");
        User user = new User();
        user.setBankAccount(bankAccount);

        return List.of(user);
    }
}
