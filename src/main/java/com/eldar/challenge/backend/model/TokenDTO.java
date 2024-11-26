package com.eldar.challenge.backend.model;

import java.util.Date;

public record TokenDTO(String token, Date expiration) {
}
