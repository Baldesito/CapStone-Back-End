package com.example.Proggetto_final_f_stack.controller;

import com.example.Proggetto_final_f_stack.payloadDTO.request.FavoritoRequest;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(properties = {"spring.profiles.active=test", "logging.level.org.springframework=DEBUG"},
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestSecurityConfig.class)
public class FavoritoControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Sql(statements = {
        "INSERT INTO utenti (id, username, password, email) VALUES (1, 'testuser', 'password', 'test@example.com')",
        "INSERT INTO voli (id, compagnia, origine, destinazione, data_partenza, data_arrivo, prezzo) VALUES (1, 'CompagniaTest', 'Origine', 'Destinazione', '2025-03-04 10:00:00', '2025-03-04 12:00:00', 100.00)"
    })
    public void testAggiungiFavorito() {
        FavoritoRequest favoritoRequest = new FavoritoRequest(1L, 1L);

        given()
            .auth().basic("testuser", "password")
            .contentType(ContentType.JSON)
            .body(favoritoRequest)
            .log().all()
        .when()
            .post("/api/preferiti/aggiungi")
        .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .body("id", notNullValue())
            .body("utenteId", equalTo(1))
            .body("voloId", equalTo(1));
    }
}