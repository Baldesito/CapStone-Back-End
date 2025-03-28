package com.example.Proggetto_final_f_stack.controller;

import com.example.Proggetto_final_f_stack.payloadDTO.request.PrenotazioneRequest;
import com.example.Proggetto_final_f_stack.security.service.TestSecurityConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(properties = {"spring.profiles.active=test", "logging.level.org.springframework=DEBUG"},
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestSecurityConfig.class)
public class PrenotazioneControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Sql(statements = {
        "INSERT INTO utenti (id, username, password, email) SELECT 1, 'testuser', 'password', 'test@example.com' WHERE NOT EXISTS (SELECT 1 FROM utenti WHERE id = 1)",
        "INSERT INTO voli (id, compagnia, origine, destinazione, data_partenza, data_arrivo, prezzo) SELECT 1, 'CompagniaTest', 'Origine', 'Destinazione', '2025-03-04 10:00:00', '2025-03-04 12:00:00', 100.00 WHERE NOT EXISTS (SELECT 1 FROM voli WHERE id = 1)"
    })
    public void testCreazionePrenotazione() {
        PrenotazioneRequest prenotazioneRequest = new PrenotazioneRequest(LocalDateTime.now(), 1L, 1L);

        given()
            .auth().basic("testuser", "password")
            .contentType(ContentType.JSON)
            .body(prenotazioneRequest)
            .log().all()
        .when()
            .post("/api/prenotazioni/crea")
        .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value()) // Cambiato da HttpStatus.OK.value() (200) a HttpStatus.CREATED.value() (201)
            .body("id", notNullValue())
            .body("utenteId", equalTo(1))
            .body("voloId", equalTo(1));
    }
}