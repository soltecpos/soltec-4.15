package com.soltec.web.controller;

import com.soltec.web.entity.Person;
import com.soltec.web.entity.Role;
import com.soltec.web.repository.PersonRepository;
import com.soltec.web.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired private PersonRepository personRepository;
    @Autowired private RoleRepository roleRepository;

    @GetMapping
    public List<Map<String, Object>> getUsers() {
        return personRepository.findAllByOrderByNameAsc().stream().map(this::mapPerson).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable String id) {
        return personRepository.findById(id)
                .map(p -> ResponseEntity.ok(mapPersonDetail(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable String id, @RequestBody Map<String, Object> body) {
        return personRepository.findById(id).map(p -> {
            if (body.containsKey("name")) p.setName((String) body.get("name"));
            if (body.containsKey("visible")) p.setVisible((Boolean) body.get("visible"));
            if (body.containsKey("roleId")) p.setRoleId((String) body.get("roleId"));
            if (body.containsKey("card")) p.setCard((String) body.get("card"));
            personRepository.save(p);
            return ResponseEntity.ok(mapPersonDetail(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Map<String, Object> createUser(@RequestBody Map<String, Object> body) {
        Person p = new Person();
        p.setId(UUID.randomUUID().toString());
        p.setName((String) body.get("name"));
        p.setRoleId((String) body.getOrDefault("roleId", "2")); // Employee by default
        p.setVisible(body.containsKey("visible") ? (Boolean) body.get("visible") : true);
        if (body.containsKey("card")) p.setCard((String) body.get("card"));
        personRepository.save(p);
        return mapPersonDetail(p);
    }

    @GetMapping("/roles")
    public List<Map<String, Object>> getRoles() {
        return roleRepository.findAll().stream().map(r -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", r.getId());
            map.put("name", r.getName());
            return map;
        }).collect(Collectors.toList());
    }

    private Map<String, Object> mapPerson(Person p) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", p.getId());
        map.put("name", p.getName());
        map.put("roleId", p.getRoleId());
        map.put("visible", p.isVisible());
        return map;
    }

    private Map<String, Object> mapPersonDetail(Person p) {
        Map<String, Object> map = mapPerson(p);
        map.put("card", p.getCard());
        Role role = roleRepository.findById(p.getRoleId()).orElse(null);
        map.put("roleName", role != null ? role.getName() : "Unknown");
        return map;
    }
}
