package com.example.Proggetto_final_f_stack.service;

import com.example.Proggetto_final_f_stack.model.Prenotazione;
import com.example.Proggetto_final_f_stack.model.Utente;
import com.example.Proggetto_final_f_stack.model.Volo;
import com.example.Proggetto_final_f_stack.payloadDTO.request.PrenotazioneRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.PrenotazioneResponse;
import com.example.Proggetto_final_f_stack.payloadDTO.response.VoloResponse;
import com.example.Proggetto_final_f_stack.repository.PrenotazioneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrenotazioneServiceTest {

    @Mock
    private PrenotazioneRepository prenotazioneRepository;

    @Mock
    private UtenteService utenteService;

    @Mock
    private VoloService voloService;

    @InjectMocks
    private PrenotazioneService prenotazioneService;

    private Utente utente;
    private Volo volo;
    private PrenotazioneRequest prenotazioneRequest;
    private Prenotazione prenotazione;

    @BeforeEach
    public void setUp() {
        utente = new Utente();
        utente.setId(1L);

        volo = new Volo();
        volo.setId(1L);

        // Inizializziamo la richiesta con una data fissa per evitare discrepanze di millisecondi nei test
        LocalDateTime data = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        prenotazioneRequest = new PrenotazioneRequest(data, 1L, 1L);

        prenotazione = new Prenotazione(data, utente, volo);
        prenotazione.setId(1L);
    }

    @Test
    public void testCreaPrenotazione() {
        when(utenteService.getUtenteById(1L)).thenReturn(utente);

        // CORREZIONE: getVoloById restituisce VoloResponse, NON Optional e NON Volo entità.
        VoloResponse voloRes = new VoloResponse(1L, "Compagnia", "Origine", "Dest", LocalDateTime.now(), LocalDateTime.now(), 100.0);
        when(voloService.getVoloById(1L)).thenReturn(voloRes);

        when(prenotazioneRepository.save(any(Prenotazione.class))).thenReturn(prenotazione);

        PrenotazioneResponse result = prenotazioneService.creaPrenotazione(prenotazioneRequest);

        assertNotNull(result);
        assertEquals(1L, result.getUtenteId());
        verify(prenotazioneRepository, times(1)).save(any(Prenotazione.class));
    }

    @Test
    public void testUpdatePrenotazione() {
        LocalDateTime dataAggiornata = LocalDateTime.now().plusHours(1).truncatedTo(ChronoUnit.SECONDS);
        PrenotazioneRequest updatedPrenotazioneRequest = new PrenotazioneRequest(dataAggiornata, 1L, 1L);

        when(prenotazioneRepository.findByIdWithDetails(1L)).thenReturn(Optional.of(prenotazione));
        when(utenteService.getUtenteById(1L)).thenReturn(utente);

        // CORREZIONE: Anche qui usiamo VoloResponse
        VoloResponse voloRes = new VoloResponse(1L, "Compagnia", "Origine", "Dest", dataAggiornata, dataAggiornata, 100.0);
        when(voloService.getVoloById(1L)).thenReturn(voloRes);

        when(prenotazioneRepository.save(any(Prenotazione.class))).thenReturn(prenotazione);

        PrenotazioneResponse result = prenotazioneService.aggiornaPrenotazione(1L, updatedPrenotazioneRequest);

        assertNotNull(result);
        verify(prenotazioneRepository, times(1)).findByIdWithDetails(1L);
    }
}