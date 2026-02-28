package com.soltec.web.controller;

import com.soltec.web.dto.AuthRequest;
import com.soltec.web.dto.AuthResponse;
import com.soltec.web.entity.Person;
import com.soltec.web.repository.PersonRepository;
import com.soltec.web.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final PersonRepository personRepository;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil, PersonRepository personRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.personRepository = personRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        Person person = personRepository.findByName(authRequest.getUsername()).get();
        
        final String jwt = jwtUtil.generateToken(userDetails.getUsername(), person.getRoleId());
        
        return ResponseEntity.ok(new AuthResponse(jwt, person.getName(), person.getRoleId()));
    }
}
