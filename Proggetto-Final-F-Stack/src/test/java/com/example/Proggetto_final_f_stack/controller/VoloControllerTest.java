package com.example.Proggetto_final_f_stack.controller;

import com.example.Proggetto_final_f_stack.payloadDTO.request.VoloRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.VoloResponse;
import com.example.Proggetto_final_f_stack.security.service.TestSecurityConfig;
import com.example.Proggetto_final_f_stack.service.VoloService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
@Import(TestSecurityConfig.class)
public class VoloControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testCreazioneVolo() {
        // Dati di input per la richiesta
        VoloRequest voloRequest = new VoloRequest(
                "CompagniaAerea", "Origine", "Destinazione",
                LocalDateTime.now(), LocalDateTime.now().plusHours(2),
                100.00);

        // Dati di output simulati dal servizio
        VoloResponse voloResponse = new VoloResponse(
                1L, "CompagniaAerea", "Origine", "Destinazione",
                LocalDateTime.now(), LocalDateTime.now().plusHours(2),
                100.00);

        // Simula l'autenticazione con un token JWT per un utente admin
        String adminToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbnVzZXIiLCJyb2xlcyI6WyJST0xFX0FETUlOIl0sImlhdCI6MTcxNzU3NjAwMCwiZXhwIjo5OTk5OTk5OTk5fQ.YourSignature";

        // Esegui la richiesta POST
        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + adminToken)
            .body(voloRequest)
        .when()
            .post("/api/voli/crea")
        .then()
            .statusCode(201)
            .body("id", equalTo(1))
            .body("compagnia", equalTo("CompagniaAerea"))
            .body("origine", equalTo("Origine"))
            .body("destinazione", equalTo("Destinazione"))
            .body("prezzo", equalTo(100.00f));
    }
}