package com.example.Proggetto_final_f_stack.service;

import com.example.Proggetto_final_f_stack.exception.ResourceNotFoundException;
import com.example.Proggetto_final_f_stack.model.Utente;
import com.example.Proggetto_final_f_stack.model.Volo;
import com.example.Proggetto_final_f_stack.payloadDTO.request.VoloRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.VoloResponse;
import com.example.Proggetto_final_f_stack.repository.UtenteRepository;
import com.example.Proggetto_final_f_stack.repository.VoloRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoloService {

    @Autowired
    private VoloRepository voloRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Transactional
    public VoloResponse creaVolo(VoloRequest voloRequest) {
        Volo volo = new Volo();
        volo.setCompagnia(voloRequest.getCompagnia());
        volo.setOrigine(voloRequest.getOrigine());
        volo.setDestinazione(voloRequest.getDestinazione());
        volo.setDataPartenza(voloRequest.getDataPartenza());
        volo.setDataArrivo(voloRequest.getDataArrivo());
        volo.setPrezzo(voloRequest.getPrezzo());


        Volo savedVolo = voloRepository.save(volo);
        return new VoloResponse(
            savedVolo.getId(),
            savedVolo.getCompagnia(),
            savedVolo.getOrigine(),
            savedVolo.getDestinazione(),
            savedVolo.getDataPartenza(),
            savedVolo.getDataArrivo(),
            savedVolo.getPrezzo()
        );
    }

    @Transactional
public VoloResponse aggiornaVolo(Long id, VoloRequest voloRequest) {
    Volo volo = voloRepository.findByIdWithDetails(id)
            .orElseThrow(() -> new RuntimeException("Volo non trovato con ID: " + id));

    System.out.println("Volo trovato: " + volo);
    System.out.println("Prenotazioni: " + volo.getPrenotazioni());
    System.out.println("Preferiti: " + volo.getPreferiti());

    // Verifica se il volo ha prenotazioni
    if (!volo.getPrenotazioni().isEmpty()) {
        // Ottieni l'utente corrente
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        System.out.println("Utente corrente: " + username);

        // Verifica se l'utente corrente è un admin
        if (!isUserAdmin(username)) {
            System.out.println("Utente non autorizzato: non è un admin");
            throw new RuntimeException("Non puoi aggiornare un volo già prenotato a meno che non sei un admin");
        } else {
            System.out.println("Utente autorizzato: è un admin");
        }
    }

    // Aggiorna i campi del volo
    volo.setCompagnia(voloRequest.getCompagnia());
    volo.setOrigine(voloRequest.getOrigine());
    volo.setDestinazione(voloRequest.getDestinazione());
    volo.setDataPartenza(voloRequest.getDataPartenza());
    volo.setDataArrivo(voloRequest.getDataArrivo());
    volo.setPrezzo(voloRequest.getPrezzo());



    Volo updatedVolo = voloRepository.save(volo);
    return new VoloResponse(
        updatedVolo.getId(),
        updatedVolo.getCompagnia(),
        updatedVolo.getOrigine(),
        updatedVolo.getDestinazione(),
        updatedVolo.getDataPartenza(),
        updatedVolo.getDataArrivo(),
        updatedVolo.getPrezzo()
    );
}

    private boolean isUserAdmin(String username) {
        Utente utente = utenteRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con username: " + username));
        return utente.getRoles().contains("ROLE_ADMIN");
    }

    @Transactional
    public void eliminaVolo(Long id) {
        if (voloRepository.existsById(id)) {
            voloRepository.deleteById(id);
        } else {
            throw new RuntimeException("Volo non trovato con ID: " + id);
        }
    }

     public List<VoloResponse> getAllVoli() {
        List<Volo> voli = voloRepository.findAll();
        return voloRepository.findAll().stream()
                .map(volo -> new VoloResponse(
                        volo.getId(),
                        volo.getCompagnia(),
                        volo.getOrigine(),
                        volo.getDestinazione(),
                        volo.getDataPartenza(),
                        volo.getDataArrivo(),
                        volo.getPrezzo()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public VoloResponse getVoloById(Long id) {
        Volo volo = voloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Volo con ID " + id + " non trovato"));
        return new VoloResponse(
                volo.getId(),
                volo.getCompagnia(),
                volo.getOrigine(),
                volo.getDestinazione(),
                volo.getDataPartenza(),
                volo.getDataArrivo(),
                volo.getPrezzo()
        );
    }

    @Transactional
    public Volo findVoloEntityById(Long id) {
        return voloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Volo con ID " + id + " non trovato"));
    }

    @Transactional
    public Volo findById(Long id) {
        return voloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Volo non trovato con ID: " + id));
    }
}
