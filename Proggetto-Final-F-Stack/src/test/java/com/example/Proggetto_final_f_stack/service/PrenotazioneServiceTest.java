package com.example.Proggetto_final_f_stack.service;

import com.example.Proggetto_final_f_stack.model.Prenotazione;
import com.example.Proggetto_final_f_stack.model.Utente;
import com.example.Proggetto_final_f_stack.model.Volo;
import com.example.Proggetto_final_f_stack.payloadDTO.request.PrenotazioneRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.PrenotazioneResponse;
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

        prenotazioneRequest = new PrenotazioneRequest(LocalDateTime.now(), 1L, 1L);

        prenotazione = new Prenotazione(LocalDateTime.now(), utente, volo);
        prenotazione.setId(1L);
    }

    @Test
    public void testCreaPrenotazione() {
        when(utenteService.getUtenteById(1L)).thenReturn(utente);
        when(voloService.getVoloById(1L));
        when(prenotazioneRepository.save(any(Prenotazione.class))).thenReturn(prenotazione);

        PrenotazioneResponse result = prenotazioneService.creaPrenotazione(prenotazioneRequest);

        assertEquals(1L, result.getUtenteId());
        assertEquals(1L, result.getVoloId());
        assertNotNull(result.getId());
        assertEquals(prenotazioneRequest.getDataPrenotazione().truncatedTo(ChronoUnit.SECONDS), result.getDataPrenotazione().truncatedTo(ChronoUnit.SECONDS));
        verify(prenotazioneRepository, times(1)).save(any(Prenotazione.class));
    }

    @Test
    public void testUpdatePrenotazione() {
        PrenotazioneRequest updatedPrenotazioneRequest = new PrenotazioneRequest(
            LocalDateTime.now().plusHours(1), 1L, 1L);

        when(prenotazioneRepository.findByIdWithDetails(1L)).thenReturn(Optional.of(prenotazione));
        when(utenteService.getUtenteById(1L)).thenReturn(utente);
        when(voloService.getVoloById(1L));
        when(prenotazioneRepository.save(any(Prenotazione.class))).thenReturn(prenotazione);

        PrenotazioneResponse result = prenotazioneService.aggiornaPrenotazione(1L, updatedPrenotazioneRequest);

        assertNotNull(result);
        assertEquals(updatedPrenotazioneRequest.getDataPrenotazione().truncatedTo(ChronoUnit.SECONDS), result.getDataPrenotazione().truncatedTo(ChronoUnit.SECONDS));
        assertEquals(1L, result.getUtenteId());
        assertEquals(1L, result.getVoloId());
        verify(prenotazioneRepository, times(1)).findByIdWithDetails(1L);
        verify(utenteService, times(1)).getUtenteById(1L);
        verify(voloService, times(1)).getVoloById(1L);
        verify(prenotazioneRepository, times(1)).save(any(Prenotazione.class));
    }
}