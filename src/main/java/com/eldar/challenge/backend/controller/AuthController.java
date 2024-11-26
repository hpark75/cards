package com.eldar.challenge.backend.controller;

import com.eldar.challenge.backend.model.TokenDTO;
import com.eldar.challenge.backend.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDTO login(Authentication authentication) {
        LOG.info("Token requested for user: '{}'", authentication);
        TokenDTO token = tokenService.generateToken(authentication);
        LOG.info("Token granted {}", token.token());
        return token;
    }
}
