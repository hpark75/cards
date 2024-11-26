package com.eldar.challenge.backend.controller;

import com.eldar.challenge.backend.model.Card;
import com.eldar.challenge.backend.model.CardDTO;
import com.eldar.challenge.backend.model.PaymentDTO;
import com.eldar.challenge.backend.service.email.EmailSender;
import com.eldar.challenge.backend.service.generator.CardNumberGenerator;
import com.eldar.challenge.backend.service.CardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class CardController {
    private final CardService cardService;
    private final CardNumberGenerator cardNumberGenerator;
    private final EmailSender emailSender;

    public CardController(CardService cardService, CardNumberGenerator cardNumberGenerator, EmailSender emailSender) {
        this.cardService = cardService;
        this.cardNumberGenerator = cardNumberGenerator;
        this.emailSender = emailSender;
    }

    @PostMapping("/cards")
    public CardDTO createCard(@RequestBody CardDTO card) {
        Card created = cardService.createNew(card, cardNumberGenerator.generateNumber(), cardNumberGenerator.generateCvv());
        emailSender.send(created.getPerson().getEmail(), "Ya tiene su nueva Tarjeta %s".formatted(created.getBrand().getName()),
                "Numero: %s CVV: %s".formatted(created.getNumber(), created.getCvv()));
        return new CardDTO(created.getId(), created.getBrand().getName(), created.getExpiration(), created.getFullname(), created.getPerson().getDni());
    }

    @GetMapping("/cards")
    public List<Card> getAll() {
        return cardService.getAll();
    }

    @PostMapping("/pay")
    public String pay(@RequestBody PaymentDTO payment) {
        if (cardService.validate(payment.number(), payment.cvv())) {
            DateFormat format = DateFormat.getDateInstance();
            emailSender.send(cardService.getCardEmail(payment.number()), "Pago realizado!",
                    "%s: Pagado %f pesos (%s)".formatted(format.format(new Date()), payment.amount(), payment.description()));
            return "Pago completado!";
        } else {
            throw new RuntimeException("The payment is not possible!");
        }
    }
}
