package com.example.Proggetto_final_f_stack.repository;

import com.example.Proggetto_final_f_stack.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    List<Favorito> findByUtenteId(Long utenteId);

    List<Favorito> findByVoloId(Long voloId);
}