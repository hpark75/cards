package com.eldar.challenge.backend.service;

import com.eldar.challenge.backend.model.Brand;
import com.eldar.challenge.backend.model.BrandDTO;
import com.eldar.challenge.backend.repo.BrandRepository;
import com.eldar.challenge.backend.service.rate.RateParser;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@Transactional
public class BrandService {
    private final BrandRepository brandRepository;
    private final RateParser rateParser;
    private final static Map<String, Function<LocalDate, Double>> factors = new ConcurrentHashMap<>();

    public BrandService(BrandRepository brandRepository, RateParser rateParser) {
        this.brandRepository = brandRepository;
        this.rateParser = rateParser;
    }

    public Brand createNew(BrandDTO brand) {
        final String name = brand.name().toUpperCase();
        if (brandRepository.findByName(name).isPresent()) {
            throw new RuntimeException("The brand %s already exists!".formatted(name));
        }
        return brandRepository.save(new Brand(name, brand.rate()));
    }

    public Double operationRate(final String brand, final Double amount, final LocalDate date) {
        final Function<LocalDate, Double> factor = factors.containsKey(brand) ? factors.get(brand) : getFactor(brand);
        Double f = factor.apply(date);
        if (f < 0.3) {
            f = 0.3;
        } else if (f > 5.0) {
            f = 5.0;
        }
        return f * amount / 100.0;
    }

    private Function<LocalDate, Double> getFactor(String brand) {
        final Optional<Brand> brandOptional = brandRepository.findByName(brand);
        if (brandOptional.isEmpty()) {
            throw new RuntimeException("The brand %s doesn't exist!".formatted(brand));
        }
        Function<LocalDate, Double> formula = rateParser.parseRateFormula(brandOptional.get().getRateFormula());
        factors.put(brand, formula);
        return formula;
    }


}
