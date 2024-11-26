package com.eldar.challenge.backend.service.rate;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class SimpleRateParser implements RateParser {
    @Override
    public Function<LocalDate, Double> parseRateFormula(String formula) {
        if (formula.contains("/")) {
            String[] operands = formula.split("/");
            return parseRateDivision(operands);
        } else if (formula.contains("*")) {
            String[] operands = formula.split("\\*");
            return parseRateMultiplication(operands);
        }
        return parseRateUnary(formula);
    }

    public Function<LocalDate, Double> parseRateDivision(String[] operands) {
        return (date) -> {
            final Double a = parseRateUnary(operands[0]).apply(date);
            final Double b = parseRateUnary(operands[1]).apply(date);
            System.out.println("%s / %s".formatted(operands[0], operands[1]));
            return a / b;
        };
    }

    public Function<LocalDate, Double> parseRateMultiplication(String[] operands) {
        return (date) -> {
            final Double a = parseRateUnary(operands[0]).apply(date);
            final Double b = parseRateUnary(operands[1]).apply(date);
            System.out.println("%s * %s".formatted(operands[0], operands[1]));
            return a * b;
        };
    }

    public Function<LocalDate, Double> parseRateUnary(String operand) {
        return switch (operand) {
            case "YY" -> (date) -> {
                System.out.println("YY %d".formatted(date.getYear()));
                                    return (double) date.getYear() % 100;
                                };
            case "MM" -> (date) -> {
                System.out.println("MM %d".formatted(date.getMonthValue()));
                return (double) date.getMonthValue();
            };
            case "DD" -> (date) -> {
                System.out.println("DD %d".formatted(date.getDayOfMonth()));
                return (double) date.getDayOfMonth();
            };
            default -> (date) -> Double.valueOf(operand);
        };
    }
}
