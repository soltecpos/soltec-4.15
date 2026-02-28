package com.soltec.web.security;

import com.soltec.web.entity.Person;
import com.soltec.web.repository.PersonRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    public CustomUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + username));
        
        return new User(person.getName(), person.getApppassword() == null ? "empty:" : person.getApppassword(), new ArrayList<>());
    }
}
