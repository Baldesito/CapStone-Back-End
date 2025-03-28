package com.example.Proggetto_final_f_stack.repository;

import com.example.Proggetto_final_f_stack.model.Favorito;
import com.example.Proggetto_final_f_stack.model.Utente;
import com.example.Proggetto_final_f_stack.model.Volo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@DataJpaTest
@ActiveProfiles("test")
public class FavoritoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FavoritoRepository favoritoRepository;

    private Utente utenteTest;
    private Volo voloTest;

    @BeforeEach
    public void setUp() {
        utenteTest = new Utente("testuser", "password", "test@example.com");
        entityManager.persist(utenteTest);

        voloTest = new Volo("CompagniaAerea", "Origine", "Destinazione", LocalDateTime.now(), LocalDateTime.now().plusHours(2), BigDecimal.valueOf(100.00));
        entityManager.persist(voloTest);

        entityManager.flush();
    }

    @Test
    public void testSaveFavorito() {
        Favorito favorito = new Favorito(utenteTest, voloTest);
        Favorito savedFavorito = favoritoRepository.save(favorito);

        Favorito retrievedFavorito = entityManager.find(Favorito.class, savedFavorito.getId());
        assertEquals(utenteTest.getId(), retrievedFavorito.getUtente().getId());
        assertEquals(voloTest.getId(), retrievedFavorito.getVolo().getId());
    }

    @Test
    public void testFindById() {
        Favorito favorito = new Favorito(utenteTest, voloTest);
        entityManager.persist(favorito);
        entityManager.flush();

        Optional<Favorito> foundFavorito = favoritoRepository.findById(favorito.getId());
        assertTrue(foundFavorito.isPresent());
        assertEquals(utenteTest.getId(), foundFavorito.get().getUtente().getId());
        assertEquals(voloTest.getId(), foundFavorito.get().getVolo().getId());
    }

    @Test
    public void testFindAll() {
        Utente utente2 = new Utente("testuser2", "password", "test2@example.com");
        entityManager.persist(utente2);
        Volo volo2 = new Volo("CompagniaAerea2", "Origine2", "Destinazione2", LocalDateTime.now(), LocalDateTime.now().plusHours(2), BigDecimal.valueOf(200.00));
        entityManager.persist(volo2);
        entityManager.flush();

        Favorito favorito1 = new Favorito(utenteTest, voloTest);
        Favorito favorito2 = new Favorito(utente2, volo2);
        entityManager.persist(favorito1);
        entityManager.persist(favorito2);
        entityManager.flush();

        List<Favorito> favoriti = favoritoRepository.findAll();
        assertEquals(2, favoriti.size());
    }

    @Test
    public void testDeleteById() {
        Favorito favorito = new Favorito(utenteTest, voloTest);
        entityManager.persist(favorito);
        entityManager.flush();

        favoritoRepository.deleteById(favorito.getId());
        Optional<Favorito> deletedFavorito = favoritoRepository.findById(favorito.getId());
        assertTrue(deletedFavorito.isEmpty());
    }
}