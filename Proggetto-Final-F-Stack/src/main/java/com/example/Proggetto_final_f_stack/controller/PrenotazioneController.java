package com.example.Proggetto_final_f_stack.controller;

import com.example.Proggetto_final_f_stack.model.Prenotazione;
import com.example.Proggetto_final_f_stack.model.Utente;
import com.example.Proggetto_final_f_stack.model.Volo;
import com.example.Proggetto_final_f_stack.payloadDTO.request.PrenotazioneRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.PrenotazioneResponse;
import com.example.Proggetto_final_f_stack.repository.PrenotazioneRepository;
import com.example.Proggetto_final_f_stack.repository.UtenteRepository;
import com.example.Proggetto_final_f_stack.repository.VoloRepository;
import com.example.Proggetto_final_f_stack.service.PrenotazioneService;
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
    PrenotazioneRepository prenotazioneRepository;

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    VoloRepository voloRepository;

    // POST --> http://localhost:8080/prenotazioni
    @PostMapping("/crea")
public ResponseEntity<PrenotazioneResponse> creaPrenotazione(
        @Valid @RequestBody PrenotazioneRequest prenotazioneRequest) {

    // Controlla se l'utente esiste
    Utente utente = utenteRepository.findById(prenotazioneRequest.getUtenteId())
            .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + prenotazioneRequest.getUtenteId()));

    // Controlla se il volo esiste
    Volo volo = voloRepository.findById(prenotazioneRequest.getVoloId())
            .orElseThrow(() -> new RuntimeException("Volo non trovato con ID: " + prenotazioneRequest.getVoloId()));

    // Crea la nuova prenotazione
    Prenotazione nuovaPrenotazione = new Prenotazione(prenotazioneRequest.getDataPrenotazione(), utente, volo);
    prenotazioneRepository.save(nuovaPrenotazione);

    return ResponseEntity.status(HttpStatus.CREATED).body(new PrenotazioneResponse());
}


    // PUT --> http://localhost:8080/prenotazioni/{id}
    @PutMapping("/{id}")
public ResponseEntity<PrenotazioneResponse> aggiornaPrenotazione(
        @PathVariable Long id,
        @Valid @RequestBody PrenotazioneRequest prenotazioneRequest) {

    // Trova la prenotazione esistente
    Prenotazione prenotazione = prenotazioneRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Prenotazione non trovata con ID: " + id));

    // Controlla se l'utente esiste
    Utente utente = utenteRepository.findById(prenotazioneRequest.getUtenteId())
            .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + prenotazioneRequest.getUtenteId()));

    // Controlla se il volo esiste
    Volo volo = voloRepository.findById(prenotazioneRequest.getVoloId())
            .orElseThrow(() -> new RuntimeException("Volo non trovato con ID: " + prenotazioneRequest.getVoloId()));

    // Aggiorna i dati della prenotazione
    prenotazione.setDataPrenotazione(prenotazioneRequest.getDataPrenotazione());
    prenotazione.setUtente(utente);
    prenotazione.setVolo(volo);

    // Salva la prenotazione aggiornata nel database
    prenotazioneRepository.save(prenotazione);

    // Costruisci il DTO di risposta
    PrenotazioneResponse response = new PrenotazioneResponse(
            prenotazione.getId(),
            prenotazione.getDataPrenotazione(),
            prenotazione.getUtente().getId(),
            prenotazione.getVolo().getId()
    );

    // Restituisci la risposta corretta
    return ResponseEntity.ok(response);
}

    // GET --> http://localhost:8080/prenotazioni
    @GetMapping
    public ResponseEntity<List<PrenotazioneResponse>> getAllPrenotazioni() {
        List<PrenotazioneResponse> response = prenotazioneService.getAllPrenotazioni();
        return ResponseEntity.ok(response);
    }

    // GET --> http://localhost:8080/prenotazioni/{id}
@GetMapping("/{id}")
public ResponseEntity<Prenotazione> getPrenotazioneById(@PathVariable Long id) {
    return prenotazioneRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            });
}




    // GET --> http://localhost:8080/prenotazioni/utente/{utenteId}
    @GetMapping("/utente/{utenteId}")
    public ResponseEntity<List<PrenotazioneResponse>> getPrenotazioniByUtente(@PathVariable Long utenteId) {
        List<PrenotazioneResponse> response = prenotazioneService.getPrenotazioniByUtente(utenteId);
        return ResponseEntity.ok(response);
    }
    // DELETE --> http://localhost:8080/prenotazioni/{Id}
    @DeleteMapping("/{id}")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<Void> deletePrenotazione(@PathVariable Long id) {

    if (!prenotazioneRepository.existsById(id)) {

        return ResponseEntity.notFound().build();
    }

    prenotazioneService.deletePrenotazione(id);

    return ResponseEntity.noContent().build();
}

}