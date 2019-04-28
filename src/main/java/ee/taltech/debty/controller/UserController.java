package ee.taltech.debty.controller;

import ee.taltech.debty.entity.BankAccount;
import ee.taltech.debty.entity.Person;
import ee.taltech.debty.model.PersonDto;
import ee.taltech.debty.security.service.SecurityService;
import ee.taltech.debty.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;

    @PostMapping("/register")
    public ResponseEntity register(@Validated @RequestBody PersonDto userForm) {

        if (userService.emailExists(userForm.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Person person = userService.saveNewUser(userForm);
        securityService.autoLogin(userForm.getEmail(), userForm.getPasswordConfirmation());

        return ResponseEntity.status(HttpStatus.OK).body(person);
    }

    @GetMapping("/loggedIn")
    public Person getLoggedInUser(Principal principal) {
        String email = principal.getName();
        return userService.getUserByEmail(email).orElse(null);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("@permissionEvaluator.isUserByEmail(#email)")
    public Person getUserByEmail(@Email @PathVariable("email") String email) {
        return userService.getUserByEmail(email).orElse(new Person());
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("@permissionEvaluator.isUserById(#id)")
    public Person getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id).orElseGet(Person::new);
    }

    @PutMapping("")
    @PreAuthorize("@permissionEvaluator.isUserById(#user.id)")
    public Person updateUser(@RequestBody PersonDto user) {
        return userService.updateUser(user);
    }

    @PostMapping("/bankAccount/{id}")
    @PreAuthorize("@permissionEvaluator.isUserById(#id)")
    public void addBankAccountForUser(@PathVariable("id") Long id, @RequestBody BankAccount bankAccount) {
        userService.addBankAccountForUser(bankAccount, id);
    }

    @PostMapping("/signout")
    public void logoutDo(HttpServletRequest request) {
        securityService.clearSecurityContext();

        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        for (Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }
    }
}
