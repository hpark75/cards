package com.eldar.challenge.backend.service;

import com.eldar.challenge.backend.model.CardDTO;
import com.eldar.challenge.backend.model.Person;
import com.eldar.challenge.backend.model.PersonDTO;
import com.eldar.challenge.backend.repo.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person createNew(PersonDTO person) {
        if (personRepository.findByDni(person.dni()).isPresent()) {
            throw new RuntimeException("The DNI %s already exists!".formatted(person.dni()));
        }
        return personRepository.save(
                new Person(person.name(), person.surname(), person.dni(), person.birthdate(), person.email(), List.of())
        );
    }

    public List<CardDTO> getCardsFrom(String dni) {
        Optional<Person> person = personRepository.findByDni(dni);
        if (person.isPresent()) {
            return person.get().getCards().stream().map(c -> new CardDTO(c.getId(), c.getBrand().getName(),
                    c.getExpiration(), c.getFullname(), c.getPerson().getDni())).collect(Collectors.toList());
        }
        return List.of();
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }
}
