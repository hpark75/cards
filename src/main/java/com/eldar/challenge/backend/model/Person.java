package com.eldar.challenge.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(unique=true)
    private String dni;

    @Temporal(TemporalType.DATE)
    @Column
    private Date birthdate;

    @Column
    private String email;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards;

    public Person(String name, String surname, String dni, Date birthdate, String email, List<Card> cards) {
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.birthdate = birthdate;
        this.email = email;
        this.cards = cards;
    }
}
