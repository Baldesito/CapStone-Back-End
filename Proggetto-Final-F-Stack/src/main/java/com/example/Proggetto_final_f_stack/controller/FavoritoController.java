package com.example.Proggetto_final_f_stack.controller;

import com.example.Proggetto_final_f_stack.model.Favorito;
import com.example.Proggetto_final_f_stack.model.Utente;
import com.example.Proggetto_final_f_stack.model.Volo;
import com.example.Proggetto_final_f_stack.payloadDTO.request.FavoritoRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.FavoritoResponse;
import com.example.Proggetto_final_f_stack.service.FavoritoService;
import com.example.Proggetto_final_f_stack.service.UtenteService;
import com.example.Proggetto_final_f_stack.service.VoloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/preferiti")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private VoloService voloService;

    private String calcolaDurata(Volo volo) {
        if (volo.getDataPartenza() != null && volo.getDataArrivo() != null) {
            Duration durata = Duration.between(volo.getDataPartenza(), volo.getDataArrivo());
            long ore = durata.toHours();
            long minuti = durata.toMinutesPart();
            return ore + "h " + minuti + "m";
        }
        return null;
    }

    @PostMapping("/aggiungi")
    public ResponseEntity<FavoritoResponse> aggiungiPreferito(@RequestBody FavoritoRequest preferitoRequest) {
        Utente utente = utenteService.getUtenteById(preferitoRequest.getUtenteId());
        Volo volo = voloService.findById(preferitoRequest.getVoloId());

        if (utente == null || volo == null) {
            throw new RuntimeException("Utente o Volo non trovato");
        }

        Favorito favorito = new Favorito(utente, volo);
        Favorito savedFavorito = favoritoService.aggiungiFavorito(favorito);

        FavoritoResponse response = new FavoritoResponse(
                savedFavorito.getId(),
                utente.getId(),
                volo.getId(),
                volo.getCompagnia(),
                volo.getOrigine(),
                volo.getDestinazione(),
                volo.getDataPartenza(),
                volo.getDataArrivo(),
                volo.getPrezzo(),
                calcolaDurata(volo),
                null
        );

        return ResponseEntity.created(URI.create("/api/preferiti/" + savedFavorito.getId())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<FavoritoResponse>> getAllPreferiti() {
        List<Favorito> favoriti = favoritoService.getAllFavoriti();
        List<FavoritoResponse> response = favoriti.stream()
                .filter(f -> f.getUtente() != null && f.getVolo() != null)
                .map(f -> new FavoritoResponse(
                        f.getId(),
                        f.getUtente().getId(),
                        f.getVolo().getId(),
                        f.getVolo().getCompagnia(),
                        f.getVolo().getOrigine(),
                        f.getVolo().getDestinazione(),
                        f.getVolo().getDataPartenza(),
                        f.getVolo().getDataArrivo(),
                        f.getVolo().getPrezzo(),
                        calcolaDurata(f.getVolo()),
                        null
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/utente/{utenteId}")
    public ResponseEntity<List<FavoritoResponse>> getFavoritiByUtente(@PathVariable Long utenteId) {
        List<Favorito> favoriti = favoritoService.getFavoritiByUtenteEntity(utenteId);

        List<FavoritoResponse> response = favoriti.stream()
                .filter(fav -> fav.getUtente() != null && fav.getVolo() != null)
                .map(fav -> new FavoritoResponse(
                        fav.getId(),
                        fav.getUtente().getId(),
                        fav.getVolo().getId(),
                        fav.getVolo().getCompagnia(),
                        fav.getVolo().getOrigine(),
                        fav.getVolo().getDestinazione(),
                        fav.getVolo().getDataPartenza(),
                        fav.getVolo().getDataArrivo(),
                        fav.getVolo().getPrezzo(),
                        calcolaDurata(fav.getVolo()),
                        null
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FavoritoResponse> aggiornaFavorito(@PathVariable Long id, @Valid @RequestBody FavoritoRequest favoritoRequest) {
        FavoritoResponse response = favoritoService.aggiornaFavorito(id, favoritoRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaFavorito(@PathVariable Long id) {
        favoritoService.eliminaFavorito(id);
        return ResponseEntity.noContent().build();
    }
}
