package com.example.Proggetto_final_f_stack.service;

import com.example.Proggetto_final_f_stack.model.Volo;
import com.example.Proggetto_final_f_stack.payloadDTO.request.VoloRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.VoloResponse;
import com.example.Proggetto_final_f_stack.repository.VoloRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VoloServiceTest {

    @Mock
    private VoloRepository voloRepository;

    @InjectMocks
    private VoloService voloService;

    private Volo volo;
    private VoloRequest voloRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        voloRequest = new VoloRequest("CompagniaAerea", "Origine", "Destinazione",
                LocalDateTime.now(), LocalDateTime.now().plusHours(2), 100.00);

        volo = new Volo();
        volo.setId(1L);
        volo.setCompagnia("CompagniaAerea");
        volo.setOrigine("Origine");
        volo.setDestinazione("Destinazione");
        volo.setDataPartenza(LocalDateTime.now());
        volo.setDataArrivo(LocalDateTime.now().plusHours(2));
        volo.setPrezzo(BigDecimal.valueOf(100.00));
    }

    @Test
    public void testGetVoloById_Success() {
        when(voloRepository.findById(1L)).thenReturn(Optional.of(volo));

        VoloResponse result = voloService.getVoloById(1L);

        assertNotNull(result);
        assertEquals("CompagniaAerea", result.getCompagnia());
        assertEquals("Origine", result.getOrigine());
        assertEquals("Destinazione", result.getDestinazione());
        assertEquals(100.00, result.getPrezzo());
    }

    @Test
    public void testGetVoloById_NotFound() {
        when(voloRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> voloService.getVoloById(2L));
        assertEquals("Volo non trovato con ID: 2", exception.getMessage());
    }

    @Test
    public void testCreaVolo() {
        when(voloRepository.save(any(Volo.class))).thenReturn(volo);

        VoloResponse result = voloService.creaVolo(voloRequest);

        assertNotNull(result);
        assertEquals("CompagniaAerea", result.getCompagnia());
        assertEquals(100.00, result.getPrezzo());
        verify(voloRepository, times(1)).save(any(Volo.class));
    }

    @Test
    public void testEliminaVolo() {
        when(voloRepository.existsById(1L)).thenReturn(true);
        doNothing().when(voloRepository).deleteById(1L);

        voloService.eliminaVolo(1L);

        verify(voloRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testAggiornaVolo() {
        VoloRequest updatedVoloRequest = new VoloRequest("NuovaCompagnia", "NuovaOrigine", "NuovaDestinazione",
                LocalDateTime.now(), LocalDateTime.now().plusHours(3), 150.00);

        when(voloRepository.findById(1L)).thenReturn(Optional.of(volo));
        when(voloRepository.save(any(Volo.class))).thenReturn(volo);

        VoloResponse result = voloService.aggiornaVolo(1L, updatedVoloRequest);

        assertNotNull(result);
        assertEquals("NuovaCompagnia", result.getCompagnia());
        assertEquals("NuovaOrigine", result.getOrigine());
        assertEquals("NuovaDestinazione", result.getDestinazione());
        assertEquals(150.00, result.getPrezzo());
    }
}