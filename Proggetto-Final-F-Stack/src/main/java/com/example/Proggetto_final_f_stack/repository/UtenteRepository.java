package com.example.Proggetto_final_f_stack.repository;

import com.example.Proggetto_final_f_stack.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Optional<Utente> findByUsername(String username);
    Optional<Utente> findByEmail(String email);
    List<Utente> findByRolesNotContaining(String role);
    List<Utente> findByRolesContaining(String role);
}
