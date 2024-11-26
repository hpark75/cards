package com.eldar.challenge.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "brand_name")
    private Brand brand;

    @Column(unique=true)
    private String number;

    @Column
    private String cvv;

    @Temporal(TemporalType.DATE)
    @Column
    private Date expiration;

    @Column
    private String fullname;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonIgnoreProperties("cards")
    private Person person;

    public Card(Brand brand, String number, String cvv, Date expiration, String fullname, Person person) {
        this.brand = brand;
        this.number = number;
        this.cvv = cvv;
        this.expiration = expiration;
        this.fullname = fullname;
        this.person = person;
    }
}
