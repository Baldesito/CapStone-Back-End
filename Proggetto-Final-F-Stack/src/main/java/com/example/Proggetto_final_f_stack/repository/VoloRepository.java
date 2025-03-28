package com.example.Proggetto_final_f_stack.repository;

import com.example.Proggetto_final_f_stack.model.Volo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VoloRepository extends JpaRepository<Volo, Long> {

    @Query("SELECT v FROM Volo v LEFT JOIN FETCH v.prenotazioni LEFT JOIN FETCH v.preferiti WHERE v.id = :id")
    Optional<Volo> findByIdWithDetails(@Param("id") Long id);
}