package com.eldar.challenge.backend.controller;

import com.eldar.challenge.backend.config.JwtFilter;
import com.eldar.challenge.backend.config.SecurityConfig;
import com.eldar.challenge.backend.model.TokenDTO;
import com.eldar.challenge.backend.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({AuthController.class})
@Import({SecurityConfig.class, TokenService.class, JwtFilter.class})
public class AuthControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TokenService tokenService;

    @Test
    void rootWhenUnAuthenticatedThen401() throws Exception {
        this.mvc.perform(get("/")).andExpect(status().isUnauthorized());
    }

    @Test
    void createUser() throws Exception {
        when(tokenService.generateToken(any())).thenReturn(mock(TokenDTO.class));
        this.mvc.perform(post("/login"))
                .andExpect(status().isCreated());
    }
}
