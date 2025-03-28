package com.example.Proggetto_final_f_stack.service;

import com.example.Proggetto_final_f_stack.model.Prenotazione;
import com.example.Proggetto_final_f_stack.model.Utente;
import com.example.Proggetto_final_f_stack.model.Volo;
import com.example.Proggetto_final_f_stack.payloadDTO.request.PrenotazioneRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.PrenotazioneResponse;
import com.example.Proggetto_final_f_stack.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private VoloService voloService;

    @Transactional
    public PrenotazioneResponse creaPrenotazione(PrenotazioneRequest prenotazioneRequest) {
        Utente utente = utenteService.getUtenteById(prenotazioneRequest.getUtenteId());
        Volo volo = voloService.findById(prenotazioneRequest.getVoloId());

        if (utente == null || volo == null) {
            throw new RuntimeException("Utente o Volo non trovato");
        }

        Prenotazione prenotazione = new Prenotazione(prenotazioneRequest.getDataPrenotazione(), utente, volo);
        Prenotazione savedPrenotazione = prenotazioneRepository.save(prenotazione);
        return new PrenotazioneResponse(
            savedPrenotazione.getId(),
            savedPrenotazione.getDataPrenotazione(),
            savedPrenotazione.getUtente().getId(),
            savedPrenotazione.getVolo().getId()
        );
    }

@Transactional
public PrenotazioneResponse aggiornaPrenotazione(Long id, PrenotazioneRequest prenotazioneRequest) {
    System.out.println("Tentativo di aggiornamento della prenotazione con ID: " + id);

    Prenotazione existingPrenotazione = prenotazioneRepository.findById(id).orElse(null);

    if (existingPrenotazione == null) {
        System.out.println("Errore: Prenotazione con ID " + id + " non trovata.");
        return null;
    }

    System.out.println("Prenotazione trovata: " + existingPrenotazione);

    Utente utente = utenteService.getUtenteById(prenotazioneRequest.getUtenteId());
    Volo volo = voloService.findById(prenotazioneRequest.getVoloId());

    if (utente == null || volo == null) {
        System.out.println("Errore: Utente o Volo non trovato.");
        return null;
    }

    existingPrenotazione.setDataPrenotazione(prenotazioneRequest.getDataPrenotazione());
    existingPrenotazione.setUtente(utente);
    existingPrenotazione.setVolo(volo);

    Prenotazione updatedPrenotazione = prenotazioneRepository.save(existingPrenotazione);
    System.out.println("Prenotazione aggiornata con successo: " + updatedPrenotazione);

    return new PrenotazioneResponse(
        updatedPrenotazione.getId(),
        updatedPrenotazione.getDataPrenotazione(),
        updatedPrenotazione.getUtente().getId(),
        updatedPrenotazione.getVolo().getId()
    );
}



    @Transactional
    public List<PrenotazioneResponse> getAllPrenotazioni() {
        return prenotazioneRepository.findAll().stream()
                .map(prenotazione -> new PrenotazioneResponse(
                    prenotazione.getId(),
                    prenotazione.getDataPrenotazione(),
                    prenotazione.getUtente().getId(),
                    prenotazione.getVolo().getId()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PrenotazioneResponse> getPrenotazioniByUtente(Long utenteId) {
        return prenotazioneRepository.findByUtenteId(utenteId).stream()
                .map(prenotazione -> new PrenotazioneResponse(
                    prenotazione.getId(),
                    prenotazione.getDataPrenotazione(),
                    prenotazione.getUtente().getId(),
                    prenotazione.getVolo().getId()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePrenotazione(Long id) {
        if (prenotazioneRepository.existsById(id)) {
            prenotazioneRepository.deleteById(id);
        } else {
            throw new RuntimeException("Prenotazione non trovata con ID: " + id);
        }
    }

    @Transactional
public PrenotazioneResponse getPrenotazioneByIdResponse(Long id) {
    System.out.println("🔍 Cerco la prenotazione nel database con ID: " + id);

    Optional<Prenotazione> prenotazioneOptional = prenotazioneRepository.findById(id);

    if (prenotazioneOptional.isEmpty()) {
        System.out.println("⚠️ Nessuna prenotazione trovata con ID: " + id);
        throw new RuntimeException("⚠️ Nessuna prenotazione trovata con ID: " + id);
    }

    Prenotazione prenotazione = prenotazioneOptional.get();
    System.out.println("✅ Prenotazione trovata: " + prenotazione);

    return new PrenotazioneResponse(
        prenotazione.getId(),
        prenotazione.getDataPrenotazione(),
        prenotazione.getUtente().getId(),
        prenotazione.getVolo().getId()
    );


}


@Transactional
public void printAllPrenotazioni() {
    List<Prenotazione> prenotazioni = prenotazioneRepository.findAll();

    if (prenotazioni.isEmpty()) {
        System.out.println("⚠️ Nessuna prenotazione trovata nel database!");
    } else {
        for (Prenotazione p : prenotazioni) {
            System.out.println("📌 Prenotazione trovata: ID=" + p.getId() + ", Utente=" + p.getUtente().getId() + ", Volo=" + p.getVolo().getId());
        }
    }
}


}