package com.eldar.challenge.backend.controller;

import com.eldar.challenge.backend.model.CardDTO;
import com.eldar.challenge.backend.model.Person;
import com.eldar.challenge.backend.model.PersonDTO;
import com.eldar.challenge.backend.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/persons")
    public Person createPerson(@RequestBody PersonDTO person) {
        return personService.createNew(person);
    }

    @GetMapping("/persons/{dni}/cards")
    public List<CardDTO> getPersonCards(@PathVariable String dni) {
        return personService.getCardsFrom(dni);
    }

    @GetMapping("/persons")
    public List<Person> getPersons() {
        return personService.getAll();
    }

}
