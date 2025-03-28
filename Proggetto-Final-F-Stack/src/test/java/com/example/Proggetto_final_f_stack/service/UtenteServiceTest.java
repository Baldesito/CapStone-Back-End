package com.example.Proggetto_final_f_stack.service;

import com.example.Proggetto_final_f_stack.model.Utente;
import com.example.Proggetto_final_f_stack.repository.UtenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UtenteServiceTest {

    @Mock
    private UtenteRepository utenteRepository;

    @InjectMocks
    private UtenteService utenteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUtenteById() {
        Utente utente = new Utente("testuser", "password", "test@example.com");
        when(utenteRepository.findById(1L)).thenReturn(Optional.of(utente));

        Utente result = utenteService.getUtenteById(1L);

        assertEquals("testuser", result.getUsername());
    }

    @Test
    public void testRegister() {
        Utente utente = new Utente("testuser", "password", "test@example.com");
        when(utenteRepository.save(any(Utente.class))).thenReturn(utente);

        Utente result = utenteService.register(utente);

        assertEquals("testuser", result.getUsername());
    }

    // Aggiungi altri test per gli altri metodi di UtenteService
}