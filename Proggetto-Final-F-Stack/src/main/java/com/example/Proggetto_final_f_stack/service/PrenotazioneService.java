package com.example.Proggetto_final_f_stack.service;

import com.example.Proggetto_final_f_stack.exception.ResourceNotFoundException;
import com.example.Proggetto_final_f_stack.model.Prenotazione;
import com.example.Proggetto_final_f_stack.model.Utente;
import com.example.Proggetto_final_f_stack.model.Volo;
import com.example.Proggetto_final_f_stack.payloadDTO.request.PrenotazioneRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.PrenotazioneResponse;
import com.example.Proggetto_final_f_stack.repository.PrenotazioneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(PrenotazioneService.class);

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
    logger.debug("Tentativo di aggiornamento della prenotazione con ID: {}", id);

    Prenotazione existingPrenotazione = prenotazioneRepository.findById(id)
        .orElseThrow(() -> {
            logger.error("Prenotazione con ID {} non trovata", id);
            return new ResourceNotFoundException("Prenotazione non trovata con ID: " + id);
        });

    logger.debug("Prenotazione trovata con ID: {}", id);

    Utente utente = utenteService.getUtenteById(prenotazioneRequest.getUtenteId());
    Volo volo = voloService.findById(prenotazioneRequest.getVoloId());

    existingPrenotazione.setDataPrenotazione(prenotazioneRequest.getDataPrenotazione());
    existingPrenotazione.setUtente(utente);
    existingPrenotazione.setVolo(volo);

    Prenotazione updatedPrenotazione = prenotazioneRepository.save(existingPrenotazione);
    logger.info("Prenotazione con ID {} aggiornata con successo", id);

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
    logger.debug("Ricerca prenotazione nel database con ID: {}", id);

    Prenotazione prenotazione = prenotazioneRepository.findById(id)
        .orElseThrow(() -> {
            logger.error("Nessuna prenotazione trovata con ID: {}", id);
            return new ResourceNotFoundException("Nessuna prenotazione trovata con ID: " + id);
        });

    logger.debug("Prenotazione trovata con ID: {}", id);

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
        logger.info("Nessuna prenotazione trovata nel database");
    } else {
        logger.info("Trovate {} prenotazioni nel database", prenotazioni.size());
        prenotazioni.forEach(p ->
            logger.debug("Prenotazione ID={}, UtenteID={}, VoloID={}",
                p.getId(), p.getUtente().getId(), p.getVolo().getId())
        );
    }
}


}