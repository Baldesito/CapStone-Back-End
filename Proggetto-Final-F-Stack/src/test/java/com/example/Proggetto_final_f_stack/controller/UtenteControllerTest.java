package com.example.Proggetto_final_f_stack.controller;

import com.example.Proggetto_final_f_stack.model.Utente;
import com.example.Proggetto_final_f_stack.payloadDTO.request.UtenteRequest;
import com.example.Proggetto_final_f_stack.security.service.TestSecurityConfig;
import com.example.Proggetto_final_f_stack.service.UtenteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UtenteController.class)
@Import(TestSecurityConfig.class) // Importa la configurazione di sicurezza per i test
public class UtenteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtenteService utenteService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "testuser", password = "password", roles = "USER") // Simula un utente autenticato
    public void testRegistrazioneUtente() throws Exception {
        UtenteRequest utenteRequest = new UtenteRequest("testuser", "password", "test@example.com");
        Utente utenteSalvato = new Utente(1L, "testuser", "password", "test@example.com", List.of("ROLE_USER"));

        when(utenteService.register(any(Utente.class))).thenReturn(utenteSalvato);

        mockMvc.perform(post("/api/utenti/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(utenteRequest)))
                .andExpect(status().isCreated()) // Cambiato da .isOk() a .isCreated() per aspettarsi 201
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }
}