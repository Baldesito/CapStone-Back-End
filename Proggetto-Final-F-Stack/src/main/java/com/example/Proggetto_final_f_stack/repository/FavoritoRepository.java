package com.example.Proggetto_final_f_stack.repository;

import com.example.Proggetto_final_f_stack.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    // Trova tutti i favoriti collegati all'ID di un utente
    List<Favorito> findByUtenteId(Long utenteId);

    // Trova tutti i favoriti collegati all'ID di un volo
    List<Favorito> findByVoloId(Long voloId);
}