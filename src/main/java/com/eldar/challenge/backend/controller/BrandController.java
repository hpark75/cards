package com.eldar.challenge.backend.controller;

import com.eldar.challenge.backend.model.Brand;
import com.eldar.challenge.backend.model.BrandDTO;
import com.eldar.challenge.backend.service.BrandService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping("/brands")
    public Brand createBrand(@RequestBody BrandDTO brand) {
        return brandService.createNew(brand);
    }

    @GetMapping("/rates/{brand}/amount/{value}")
    public Double calculateRate(@PathVariable String brand, @PathVariable String value) {
        return brandService.operationRate(brand.toUpperCase(), Double.valueOf(value), LocalDate.now());
    }

    @GetMapping("/rates/{brand}/amount/{value}/date/{date}")
    public Double calculateRate(@PathVariable String brand, @PathVariable String value, @PathVariable String date) {
        return brandService.operationRate(brand.toUpperCase(), Double.valueOf(value),
                LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }
}
