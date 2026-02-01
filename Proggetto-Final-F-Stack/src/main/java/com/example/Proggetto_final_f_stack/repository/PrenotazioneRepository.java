package com.example.Proggetto_final_f_stack.repository;

import com.example.Proggetto_final_f_stack.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    List<Prenotazione> findByUtenteId(Long utenteId);

    // Questo metodo usa FETCH per caricare utente e volo in una sola chiamata
    @Query("SELECT p FROM Prenotazione p LEFT JOIN FETCH p.utente LEFT JOIN FETCH p.volo WHERE p.id = :id")
    Optional<Prenotazione> findByIdWithDetails(@Param("id") Long id);
}