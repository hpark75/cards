package com.eldar.challenge.console;

import com.eldar.challenge.backend.controller.AuthController;
import com.eldar.challenge.backend.model.CardDTO;
import com.eldar.challenge.backend.model.Person;
import com.eldar.challenge.backend.model.PersonDTO;
import com.eldar.challenge.backend.model.TokenDTO;
import com.eldar.challenge.backend.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConsoleApplication {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
    private final static RestClient client = new SimpleRestClientBuilder().build(getBaseUrl());
    private static String jwtToken = "";

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            jwtToken = login(input);
            menu(input);
        }
    }

    private static void menu(Scanner input) {
        String option = "";
        while (!option.equals("0")) {
            System.out.println("Alta de Personas y Tarjetas:");
            System.out.println();
            System.out.println("1) Alta de Persona");
            System.out.println("2) Alta de Tarjeta");
            System.out.println("3) Consulta de Tarjetas por DNI");
            System.out.println("4) Consulta de Tasa");
            System.out.println("0) Terminar");
            System.out.println("Ingrese opcion:");
            int opt = Integer.valueOf(input.nextLine());
            if (opt > 0 && opt <= 4) {
                operation(opt, input);
            }
            System.out.println();
        }
    }

    private static void operation(int option, Scanner input) {
        switch (option) {
            case 1 -> ConsoleApplication.createPerson(input);
            case 2 -> ConsoleApplication.createCard(input);
            case 3 -> ConsoleApplication.getCards(input);
            case 4 -> ConsoleApplication.getRate(input);
        }
    }

    private static void createPerson(Scanner input) {
        System.out.println("Alta de Persona:");
        System.out.println();
        System.out.println("Ingrese nombre: ");
        String name = input.nextLine();
        System.out.println("Ingrese apellido: ");
        String surname = input.nextLine();
        System.out.println("Ingrese DNI: ");
        String dni = input.nextLine();
        System.out.println("Ingrese fecha de nacimiento (formato: yyyy-MM-dd): ");
        String birthdate = input.nextLine();
        System.out.println("Ingrese email: ");
        String email = input.nextLine();
        PersonDTO body = new PersonDTO(name, surname, dni,
                Date.from(LocalDate.parse(birthdate).atStartOfDay(ZoneId.systemDefault()).toInstant()), email);
        System.out.println(jwtToken);
        Person person = client.post().uri("/persons")
                .header("Authorization", "Bearer " + jwtToken)
                .body(body).retrieve().body(Person.class);
        System.out.println(person.toString());
    }

    private static void createCard(Scanner input) {
        System.out.println("Alta de Tarjeta:");
        System.out.println();
        System.out.println("Ingrese DNI: ");
        String dni = input.nextLine();
        System.out.println("Ingrese marca: ");
        String brand = input.nextLine();
        System.out.println("Ingrese fecha de vencimiento (formato: yyyy-MM-dd): ");
        String expiration = input.nextLine();
        System.out.println("Ingrese nombre completo: ");
        String fullname = input.nextLine();
        CardDTO body = new CardDTO(null, brand,
                Date.from(LocalDate.parse(expiration).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                fullname, dni);
        CardDTO card = client.post().uri("/cards")
                .header("Authorization", "Bearer " + jwtToken)
                .body(body).retrieve().body(CardDTO.class);
        System.out.println(card.toString());
    }

    private static void getCards(Scanner input) {
        System.out.println("Consulta de Tarjetas por DNI:");
        System.out.println();
        System.out.println("Ingrese DNI: ");
        String dni = input.nextLine();
        List<CardDTO> cards = client.get().uri("/persons/%s/cards".formatted(dni))
                .header("Authorization", "Bearer " + jwtToken)
                .retrieve().body(List.class);
        System.out.println(cards.toString());
    }

    private static void getRate(Scanner input) {
        System.out.println("Alta de Tasas:");
        System.out.println();
        System.out.println("Ingrese marca: ");
        String brand = input.nextLine();
        System.out.println("Ingrese monto: ");
        String amount = input.nextLine();
        System.out.println("Ingrese fecha (formato: dd-MM-yyyy) [presione Enter para la fecha actual]: ");
        String date = input.nextLine();
        if ("".equals(date)) {
            Double value = client.get().uri("/rates/%s/amount/%s".formatted(brand, amount))
                    .header("Authorization", "Bearer " + jwtToken)
                    .retrieve().body(Double.class);
            System.out.println(value.toString());
        } else {
            Double value = client.get().uri("/rates/%s/amount/%s/date/%s".formatted(brand, amount, date))
                    .header("Authorization", "Bearer " + jwtToken)
                    .retrieve().body(Double.class);
            System.out.println(value.toString());
        }
    }

    private static String getBaseUrl() {
        try (InputStream in = ConsoleApplication.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();

            if (in == null) {
                System.out.println("Sorry, unable to find application.properties");
                return null;
            }

            prop.load(in);

            return prop.getProperty("console.url");

        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    private static String login(Scanner input) {
        TokenDTO token = null;
        while (token == null) {
            try {
                System.out.println("Alta de Personas y Tarjetas: Login");
                System.out.println();
                System.out.println("Ingrese usuario: ");
                String user = input.nextLine();
                System.out.println("Ingrese password: ");
                String password = input.nextLine();
                Base64.Encoder encoder = Base64.getEncoder();
                String auth = encoder.encodeToString("%s:%s".formatted(user, password).getBytes());
                System.out.println(auth);
                token = client.post().uri("/login")
                        .header("Authorization", "Basic " + auth).retrieve().body(TokenDTO.class);
                System.out.println(token.token());
            } catch (RestClientException e) {
                LOG.error(e.getLocalizedMessage());
            }
        }
        return token.token();
    }
}
