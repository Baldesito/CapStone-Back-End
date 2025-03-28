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

@Query("SELECT p FROM Prenotazione p WHERE p.id = :id")
Optional<Prenotazione> findById(@Param("id") Long id);






}