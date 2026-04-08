package com.example.Proggetto_final_f_stack.controller;

import com.example.Proggetto_final_f_stack.model.Utente;
import com.example.Proggetto_final_f_stack.payloadDTO.request.PrenotazioneRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.PrenotazioneResponse;
import com.example.Proggetto_final_f_stack.service.EmailService;
import com.example.Proggetto_final_f_stack.service.PrenotazioneService;
import com.example.Proggetto_final_f_stack.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UtenteService utenteService;

    // POST --> http://localhost:8080/api/prenotazioni/crea
    @PostMapping("/crea")
    public ResponseEntity<PrenotazioneResponse> creaPrenotazione(@Valid @RequestBody PrenotazioneRequest request) {

        // 1. Deleghiamo al Service la creazione (il Service mapperà in automatico i dati del volo!)
        PrenotazioneResponse response = prenotazioneService.creaPrenotazione(request);

        // 2. Recuperiamo l'utente dal database per avere il suo indirizzo email reale
        Utente utente = utenteService.getUtenteById(request.getUtenteId());

        // 3. Inviamo l'email di conferma se l'utente ha una mail valida
        if (utente != null && utente.getEmail() != null) {
            String nomeDestinatario = utente.getUsername() != null ? utente.getUsername() : "Passeggero";

            // Possiamo prendere origine e destinazione direttamente dalla response!
            String tratta = response.getOrigine() + " -> " + response.getDestinazione();

            // Invio effettivo della mail
            // emailService.inviaEmailConferma(utente.getEmail(), nomeDestinatario, tratta);
        }

        // 4. Restituiamo la risposta COMPLETA al frontend (così le card non sono vuote)
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT --> http://localhost:8080/api/prenotazioni/{id}
    @PutMapping("/{id}")
    public ResponseEntity<PrenotazioneResponse> aggiornaPrenotazione(@PathVariable Long id, @Valid @RequestBody PrenotazioneRequest request) {
        PrenotazioneResponse response = prenotazioneService.aggiornaPrenotazione(id, request);
        return ResponseEntity.ok(response);
    }

    // GET --> http://localhost:8080/api/prenotazioni
    @GetMapping
    public ResponseEntity<List<PrenotazioneResponse>> getAllPrenotazioni() {
        return ResponseEntity.ok(prenotazioneService.getAllPrenotazioni());
    }

    // GET --> http://localhost:8080/api/prenotazioni/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PrenotazioneResponse> getPrenotazioneById(@PathVariable Long id) {
        return ResponseEntity.ok(prenotazioneService.getPrenotazioneByIdResponse(id));
    }

    // GET --> http://localhost:8080/api/prenotazioni/utente/{utenteId}
    @GetMapping("/utente/{utenteId}")
    public ResponseEntity<List<PrenotazioneResponse>> getPrenotazioniByUtente(@PathVariable Long utenteId) {
        List<PrenotazioneResponse> response = prenotazioneService.getPrenotazioniByUtente(utenteId);
        return ResponseEntity.ok(response);
    }

    // DELETE --> http://localhost:8080/api/prenotazioni/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePrenotazione(@PathVariable Long id) {
        prenotazioneService.deletePrenotazione(id);
        return ResponseEntity.noContent().build();
    }
}