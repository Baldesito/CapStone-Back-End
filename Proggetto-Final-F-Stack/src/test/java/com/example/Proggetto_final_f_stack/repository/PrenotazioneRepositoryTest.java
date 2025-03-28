package com.example.Proggetto_final_f_stack.repository;

import com.example.Proggetto_final_f_stack.model.Prenotazione;
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
public class PrenotazioneRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

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
    public void testSavePrenotazione() {
        Prenotazione prenotazione = new Prenotazione(LocalDateTime.now(), utenteTest, voloTest);
        Prenotazione savedPrenotazione = prenotazioneRepository.save(prenotazione);

        Prenotazione retrievedPrenotazione = entityManager.find(Prenotazione.class, savedPrenotazione.getId());
        assertEquals(utenteTest.getId(), retrievedPrenotazione.getUtente().getId());
        assertEquals(voloTest.getId(), retrievedPrenotazione.getVolo().getId());
    }

    @Test
    public void testFindById() {
        Prenotazione prenotazione = new Prenotazione(LocalDateTime.now(), utenteTest, voloTest);
        entityManager.persist(prenotazione);
        entityManager.flush();

        Optional<Prenotazione> foundPrenotazione = prenotazioneRepository.findById(prenotazione.getId());
        assertTrue(foundPrenotazione.isPresent());
        assertEquals(utenteTest.getId(), foundPrenotazione.get().getUtente().getId());
        assertEquals(voloTest.getId(), foundPrenotazione.get().getVolo().getId());
    }

    @Test
    public void testFindAll() {
        Utente utente2 = new Utente("testuser2", "password", "test2@example.com");
        entityManager.persist(utente2);
        Volo volo2 = new Volo("CompagniaAerea2", "Origine2", "Destinazione2", LocalDateTime.now(), LocalDateTime.now().plusHours(2), BigDecimal.valueOf(200.00));
        entityManager.persist(volo2);
        entityManager.flush();

        Prenotazione prenotazione1 = new Prenotazione(LocalDateTime.now(), utenteTest, voloTest);
        Prenotazione prenotazione2 = new Prenotazione(LocalDateTime.now(), utente2, volo2);
        entityManager.persist(prenotazione1);
        entityManager.persist(prenotazione2);
        entityManager.flush();

        List<Prenotazione> prenotazioni = prenotazioneRepository.findAll();
        assertEquals(2, prenotazioni.size());
    }

    @Test
    public void testDeleteById() {
        Prenotazione prenotazione = new Prenotazione(LocalDateTime.now(), utenteTest, voloTest);
        entityManager.persist(prenotazione);
        entityManager.flush();

        prenotazioneRepository.deleteById(prenotazione.getId());
        Optional<Prenotazione> deletedPrenotazione = prenotazioneRepository.findById(prenotazione.getId());
        assertTrue(deletedPrenotazione.isEmpty());
    }
}