package com.eldar.challenge.backend.service;

import com.eldar.challenge.backend.model.Brand;
import com.eldar.challenge.backend.model.Card;
import com.eldar.challenge.backend.model.CardDTO;
import com.eldar.challenge.backend.model.Person;
import com.eldar.challenge.backend.repo.BrandRepository;
import com.eldar.challenge.backend.repo.CardRepository;
import com.eldar.challenge.backend.repo.PersonRepository;
import com.eldar.challenge.backend.service.email.EmailSender;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CardService {
    private final PersonRepository personRepository;
    private final CardRepository cardRepository;
    private final BrandRepository brandRepository;

    public CardService(PersonRepository personRepository, CardRepository cardRepository, BrandRepository brandRepository) {
        this.personRepository = personRepository;
        this.cardRepository = cardRepository;
        this.brandRepository = brandRepository;
    }

    public Card createNew(CardDTO card, String number, String cvv) {
        final Optional<Person> person = personRepository.findByDni(card.dni());
        if (person.isEmpty()) {
            throw new RuntimeException("The person with DNI %s doesn't exist!".formatted(card.dni()));
        }
        /*if (cardRepository.findByNumber(number).isPresent()) {
            throw new RuntimeException("The card number already exists!".formatted(card.dni()));
        }*/
        final Optional<Brand> brandOptional = brandRepository.findByName(card.brand().toUpperCase());
        if (brandOptional.isEmpty()) {
            throw new RuntimeException("The brand %s doesn't exist!".formatted(card.brand()));
        }
        return cardRepository.save(
                new Card(brandOptional.get(), number, cvv, card.expiration(), card.fullname().toUpperCase(), person.get())
        );
    }

    public Boolean validate(String number, String cvv) {
        Optional<Card> card = cardRepository.findByNumber(number);
        if (card.isPresent()) {
            if (card.get().getCvv().equals(cvv)) {
                return true;
            }
        }
        return false;
    }

    public String getCardEmail(String number) {
        Optional<Card> card = cardRepository.findByNumber(number);
        if (card.isPresent()) {
            return card.get().getPerson().getEmail();
        }
        return "";
    }

    public List<Card> getAll() {
        return cardRepository.findAll();
    }
}
