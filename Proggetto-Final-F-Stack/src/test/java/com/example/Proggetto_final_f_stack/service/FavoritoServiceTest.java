package com.example.Proggetto_final_f_stack.service;

import com.example.Proggetto_final_f_stack.model.Favorito;
import com.example.Proggetto_final_f_stack.model.Utente;
import com.example.Proggetto_final_f_stack.model.Volo;
import com.example.Proggetto_final_f_stack.payloadDTO.request.FavoritoRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.FavoritoResponse;
import com.example.Proggetto_final_f_stack.repository.FavoritoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FavoritoServiceTest {

    @Mock
    private FavoritoRepository favoritoRepository;

    @Mock
    private UtenteService utenteService;

    @Mock
    private VoloService voloService;

    @InjectMocks
    private FavoritoService favoritoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFavoritoById() {
        Utente utente = new Utente();
        utente.setId(1L);
        Volo volo = new Volo();
        volo.setId(1L);
        Favorito favorito = new Favorito(utente, volo); // Usa il costruttore corretto
        when(favoritoRepository.findById(1L)).thenReturn(Optional.of(favorito));

        FavoritoResponse result = favoritoService.getFavoritoById(1L);

        assertEquals(1L, result.getUtenteId()); // Accedi all'ID tramite getUtente()
        assertEquals(1L, result.getVoloId()); // Accedi all'ID tramite getVolo()
    }

    @Test
    public void testAggiungiFavorito() {
        Utente utente = new Utente();
        utente.setId(1L);
        Volo volo = new Volo();
        volo.setId(1L);
        Favorito favorito = new Favorito(utente, volo); // Usa il costruttore corretto
        when(favoritoRepository.save(any(Favorito.class))).thenReturn(favorito);

        Favorito result = favoritoService.aggiungiFavorito(favorito);

        assertEquals(1L, result.getUtente().getId()); // Accedi all'ID tramite getUtente()
    }

    @Test
    public void testAggiornaFavorito() {
        Utente utente = new Utente();
        utente.setId(1L);
        Volo volo = new Volo();
        volo.setId(1L);
        Favorito favorito = new Favorito(utente, volo);
        FavoritoRequest favoritoRequest = new FavoritoRequest(1L, 1L);

        when(favoritoRepository.findById(1L)).thenReturn(Optional.of(favorito));
        when(utenteService.getUtenteById(1L)).thenReturn(utente);
        when(voloService.findById(1L)).thenReturn(volo);
        when(favoritoRepository.save(any(Favorito.class))).thenReturn(favorito);

        FavoritoResponse result = favoritoService.aggiornaFavorito(1L, favoritoRequest);

        assertEquals(1L, result.getUtenteId());
        assertEquals(1L, result.getVoloId());
    }

    // Aggiungi altri test per gli altri metodi di FavoritoService
}
