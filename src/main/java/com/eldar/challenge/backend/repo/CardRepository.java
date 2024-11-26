package com.eldar.challenge.backend.repo;

import com.eldar.challenge.backend.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
    Optional<Card> findByNumber(String number);
}
