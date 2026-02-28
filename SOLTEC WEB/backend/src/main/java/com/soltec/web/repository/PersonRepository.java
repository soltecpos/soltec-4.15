package com.soltec.web.repository;

import com.soltec.web.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {
    Optional<Person> findByName(String name);
    List<Person> findByVisibleTrue();
    List<Person> findAllByOrderByNameAsc();
}
