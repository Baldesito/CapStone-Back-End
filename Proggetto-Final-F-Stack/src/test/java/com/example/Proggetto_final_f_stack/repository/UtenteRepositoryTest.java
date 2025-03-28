package com.example.Proggetto_final_f_stack.repository;

import com.example.Proggetto_final_f_stack.model.Utente;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

@Transactional
@DataJpaTest
@ActiveProfiles("test")
public class UtenteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UtenteRepository utenteRepository;

    @Test
    public void testSaveUtente() {
        Utente utente = new Utente("testuser", "password", "test@example.com");
        Utente savedUtente = utenteRepository.save(utente);

        Utente retrievedUtente = entityManager.find(Utente.class, savedUtente.getId());
        assertEquals("testuser", retrievedUtente.getUsername());
    }

    @Test
    public void testFindById() {
        Utente utente = new Utente("testuser", "password", "test@example.com");
        entityManager.persist(utente);
        entityManager.flush();

        Optional<Utente> foundUtente = utenteRepository.findById(utente.getId());
        assertTrue(foundUtente.isPresent());
        assertEquals("testuser", foundUtente.get().getUsername());
    }

    @Test
    public void testFindByUsername() {
        Utente utente = new Utente("testuser", "password", "test@example.com");
        entityManager.persist(utente);
        entityManager.flush();

        Optional<Utente> foundUtente = utenteRepository.findByUsername("testuser");
        assertTrue(foundUtente.isPresent());
        assertEquals("testuser", foundUtente.get().getUsername());
    }

    @Test
    public void testFindAll() {
        Utente utente1 = new Utente("testuser1", "password", "test1@example.com");
        Utente utente2 = new Utente("testuser2", "password", "test2@example.com");
        entityManager.persist(utente1);
        entityManager.persist(utente2);
        entityManager.flush();

        List<Utente> utenti = utenteRepository.findAll();
        assertEquals(2, utenti.size());
    }

    @Test
    public void testDeleteById() {
        Utente utente = new Utente("testuser", "password", "test@example.com");
        entityManager.persist(utente);
        entityManager.flush();

        utenteRepository.deleteById(utente.getId());
        Optional<Utente> deletedUtente = utenteRepository.findById(utente.getId());
        assertTrue(deletedUtente.isEmpty());
    }
}