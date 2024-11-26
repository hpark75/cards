package com.eldar.challenge.backend.repo;

import com.eldar.challenge.backend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    Optional<Person> findByDni(String dni);
}
