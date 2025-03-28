package com.example.Proggetto_final_f_stack.controller;

import com.example.Proggetto_final_f_stack.payloadDTO.request.VoloRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.PrenotazioneResponse;
import com.example.Proggetto_final_f_stack.payloadDTO.response.VoloResponse;
import com.example.Proggetto_final_f_stack.service.PrenotazioneService;
import com.example.Proggetto_final_f_stack.service.VoloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voli")
public class VoloController {

    @Autowired
    private VoloService voloService;

    @Autowired
    PrenotazioneService prenotazioneService;

    @PostMapping("/crea")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<VoloResponse> creaVolo(@RequestBody VoloRequest voloRequest) {
        VoloResponse response = voloService.creaVolo(voloRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping
public ResponseEntity<VoloResponse> salvaVoloDaUtente(@RequestBody VoloRequest voloRequest) {
    VoloResponse response = voloService.creaVolo(voloRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}


    @GetMapping("/utente/{utenteId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PrenotazioneResponse>> getPrenotazioniUtente(@PathVariable Long utenteId) {
    System.out.println("Admin sta richiedendo le prenotazioni dell'utente con ID: " + utenteId);

    List<PrenotazioneResponse> prenotazioni = prenotazioneService.getPrenotazioniByUtente(utenteId);

    if (prenotazioni.isEmpty()) {
        System.out.println("Nessuna prenotazione trovata per l'utente con ID: " + utenteId);
        return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(prenotazioni);
}


    // Solo admin può aggiornare un volo
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<VoloResponse> aggiornaVolo(@PathVariable Long id,@Valid @RequestBody VoloRequest voloRequest) {
        VoloResponse response = voloService.aggiornaVolo(id, voloRequest);
        return ResponseEntity.ok(response);
    }

    // Solo admin può eliminare un volo
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> eliminaVolo( @Valid @PathVariable Long id) {
        voloService.eliminaVolo(id);
        return ResponseEntity.noContent().build();
    }

    // Tutti gli utenti possono visualizzare i voli
    @GetMapping
    public ResponseEntity<List<VoloResponse>> getAllVoli() {
        List<VoloResponse> response = voloService.getAllVoli();
        return ResponseEntity.ok(response);
    }

    // Tutti gli utenti possono visualizzare un volo specifico
    @GetMapping("/{id}")
    public ResponseEntity<VoloResponse> getVoloById(@PathVariable Long id) {
        VoloResponse response = voloService.getVoloById(id);
        return ResponseEntity.ok(response);
    }
}