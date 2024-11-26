package com.eldar.challenge.backend.service.rate;

import java.time.LocalDate;
import java.util.function.Function;

public interface RateParser {
    Function<LocalDate, Double> parseRateFormula(final String formula);
}
