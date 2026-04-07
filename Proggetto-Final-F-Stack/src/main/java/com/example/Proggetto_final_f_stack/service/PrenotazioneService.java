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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public PrenotazioneResponse creaPrenotazione(PrenotazioneRequest request) {
        Utente utente = utenteService.getUtenteById(request.getUtenteId());
        Volo volo = voloService.findById(request.getVoloId());

        if (utente == null || volo == null) {
            throw new RuntimeException("Utente o Volo non trovato");
        }

        Prenotazione prenotazione = new Prenotazione(request.getDataPrenotazione().atStartOfDay(), utente, volo);
        Prenotazione saved = prenotazioneRepository.save(prenotazione);

        return new PrenotazioneResponse(saved); // Usa il costruttore intelligente!
    }

    @Transactional
    public PrenotazioneResponse aggiornaPrenotazione(Long id, PrenotazioneRequest request) {
        Prenotazione existing = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prenotazione non trovata con ID: " + id));

        Utente utente = utenteService.getUtenteById(request.getUtenteId());
        Volo volo = voloService.findById(request.getVoloId());

        existing.setDataPrenotazione(request.getDataPrenotazione().atStartOfDay());
        existing.setUtente(utente);
        existing.setVolo(volo);

        Prenotazione updated = prenotazioneRepository.save(existing);
        return new PrenotazioneResponse(updated);
    }

    @Transactional(readOnly = true)
    public List<PrenotazioneResponse> getAllPrenotazioni() {
        return prenotazioneRepository.findAll().stream()
                .map(PrenotazioneResponse::new) // Mappa tutto il volo!
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PrenotazioneResponse> getPrenotazioniByUtente(Long utenteId) {
        return prenotazioneRepository.findByUtenteId(utenteId).stream()
                .map(PrenotazioneResponse::new) // Mappa tutto il volo!
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

    @Transactional(readOnly = true)
    public PrenotazioneResponse getPrenotazioneByIdResponse(Long id) {
        Prenotazione prenotazione = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nessuna prenotazione trovata con ID: " + id));
        return new PrenotazioneResponse(prenotazione);
    }
}