package ee.taltech.debty.security.service;

import ee.taltech.debty.entity.Person;
import ee.taltech.debty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        Person person = userRepository.findByEmail(email);

        if (person == null) throw new UsernameNotFoundException("");

        return new org.springframework.security.core.userdetails.User(person.getEmail(), person.getPassword(),
                new HashSet<GrantedAuthority>(Collections.singletonList(new SimpleGrantedAuthority("USER"))));
    }
}