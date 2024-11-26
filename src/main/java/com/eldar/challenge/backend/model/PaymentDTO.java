package com.eldar.challenge.backend.model;

public record PaymentDTO(Double amount, String description, String number, String cvv) {
}
