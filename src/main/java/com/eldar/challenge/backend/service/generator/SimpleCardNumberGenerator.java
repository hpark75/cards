package com.eldar.challenge.backend.service.generator;

import com.eldar.challenge.backend.repo.CardRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SimpleCardNumberGenerator implements CardNumberGenerator {
    private final CardRepository cardRepository;
    private final Random generator;

    public SimpleCardNumberGenerator(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
        this.generator = new Random(System.currentTimeMillis());
    }

    public String generateNumber() {
        String number = generateCandidate();
        while (cardRepository.findByNumber(number).isPresent()) {
            number = generateCandidate();
        }
        return number;
    }

    private String generateCandidate() {
        int a = generator.nextInt(100000000);
        int b = generator.nextInt(100000000);
        return "%08d%08d".formatted(a, b);
    }

    public String generateCvv() {
        return "%03d".formatted(generator.nextInt(1000));
    }
}
