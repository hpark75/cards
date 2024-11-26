package com.eldar.challenge.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Brand {
    @Id
    @Column(unique=true)
    private String name;

    @Column
    private String rateFormula;

    public Brand(String name, String rateFormula) {
        this.name = name;
        this.rateFormula = rateFormula;
    }
}
