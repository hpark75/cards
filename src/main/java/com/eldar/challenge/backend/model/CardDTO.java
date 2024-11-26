package com.eldar.challenge.backend.model;

import java.util.Date;
import java.util.UUID;

public record CardDTO(UUID id, String brand, Date expiration, String fullname, String dni) {
}
