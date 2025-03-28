package com.example.Proggetto_final_f_stack.repository;

import com.example.Proggetto_final_f_stack.model.Volo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@DataJpaTest
@ActiveProfiles("test")
public class VoloRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VoloRepository voloRepository;

    @Test
    public void testSaveVolo() {
        Volo volo = new Volo("CompagniaAerea", "Origine", "Destinazione",
                LocalDateTime.now(), LocalDateTime.now().plusHours(2), 100.00);
        Volo savedVolo = voloRepository.save(volo);

        Volo retrievedVolo = entityManager.find(Volo.class, savedVolo.getId());
        assertEquals("CompagniaAerea", retrievedVolo.getCompagnia());
    }

    @Test
    public void testFindById() {
        Volo volo = new Volo("CompagniaAerea", "Origine", "Destinazione",
                LocalDateTime.now(), LocalDateTime.now().plusHours(2), 100.00);
        entityManager.persist(volo);
        entityManager.flush();

        Optional<Volo> foundVolo = voloRepository.findById(volo.getId());
        assertTrue(foundVolo.isPresent());
        assertEquals("CompagniaAerea", foundVolo.get().getCompagnia());
    }

    @Test
    public void testFindAll() {
        Volo volo1 = new Volo("CompagniaAerea1", "Origine1", "Destinazione1",
                LocalDateTime.now(), LocalDateTime.now().plusHours(2), 100.00);
        Volo volo2 = new Volo("CompagniaAerea2", "Origine2", "Destinazione2",
                LocalDateTime.now(), LocalDateTime.now().plusHours(2), 200.00);
        entityManager.persist(volo1);
        entityManager.persist(volo2);
        entityManager.flush();

        List<Volo> voli = voloRepository.findAll();
        assertEquals(2, voli.size());
    }

    @Test
    public void testDeleteById() {
        Volo volo = new Volo("CompagniaAerea", "Origine", "Destinazione",
                LocalDateTime.now(), LocalDateTime.now().plusHours(2), 100.00);
        entityManager.persist(volo);
        entityManager.flush();

        voloRepository.deleteById(volo.getId());
        Optional<Volo> deletedVolo = voloRepository.findById(volo.getId());
        assertTrue(deletedVolo.isEmpty());
    }
}