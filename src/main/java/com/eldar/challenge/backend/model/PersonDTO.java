package com.eldar.challenge.backend.model;

import java.util.Date;

public record PersonDTO(String name, String surname, String dni, Date birthdate, String email) {
}
