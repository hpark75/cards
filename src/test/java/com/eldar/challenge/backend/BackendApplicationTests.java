package com.eldar.challenge.backend;

import com.eldar.challenge.backend.service.BrandService;
import com.eldar.challenge.backend.service.CardService;
import com.eldar.challenge.backend.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.eldar.challenge.backend.service.PersonService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations="classpath:application.properties")
class BackendApplicationTests {

	@Autowired
	PersonService personService;

	@Autowired
	TokenService tokenService;

	@Autowired
	CardService cardService;

	@Autowired
	BrandService brandService;

	@Test
	void contextLoads() {
		assertThat(personService).isNotNull();
	}

}
