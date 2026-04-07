package com.example.Proggetto_final_f_stack.service;

import com.example.Proggetto_final_f_stack.model.Favorito;
import com.example.Proggetto_final_f_stack.model.Utente;
import com.example.Proggetto_final_f_stack.model.Volo;
import com.example.Proggetto_final_f_stack.payloadDTO.request.FavoritoRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.FavoritoResponse;
import com.example.Proggetto_final_f_stack.repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private VoloService voloService;

    // Metodo principale usato dal Controller per il salvataggio
    @Transactional
    public Favorito aggiungiFavorito(Favorito favorito) {
        System.out.println("Salvataggio Favorito per Utente: " + favorito.getUtente().getId() + " e Volo: " + favorito.getVolo().getId());
        return favoritoRepository.save(favorito);
    }

    public List<Favorito> getFavoritiByUtenteEntity(Long utenteId) {
        return favoritoRepository.findByUtenteId(utenteId).stream()
                .filter(fav -> fav.getUtente() != null && fav.getVolo() != null)
                .collect(Collectors.toList());
    }

    public List<Favorito> getAllFavoriti() {
        return favoritoRepository.findAll().stream()
                .filter(favorito -> favorito.getUtente() != null && favorito.getVolo() != null)
                .collect(Collectors.toList());
    }

    @Transactional
    public void eliminaFavorito(Long id) {
        if (favoritoRepository.existsById(id)) {
            favoritoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Favorito non trovato con ID: " + id);
        }
    }

    @Transactional
    public FavoritoResponse aggiornaFavorito(Long id, FavoritoRequest favoritoRequest) {
        Favorito favorito = favoritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorito non trovato con ID: " + id));

        Utente utente = utenteService.getUtenteById(favoritoRequest.getUtenteId());
        Volo volo = voloService.findById(favoritoRequest.getVoloId());

        if (utente == null || volo == null) {
            throw new RuntimeException("Aggiornamento fallito: Utente o Volo non trovato");
        }

        favorito.setUtente(utente);
        favorito.setVolo(volo);
        Favorito updated = favoritoRepository.save(favorito);

        return new FavoritoResponse(updated.getId(), utente.getId(), volo.getId());
    }
}