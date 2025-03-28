package com.example.Proggetto_final_f_stack.controller;

import com.example.Proggetto_final_f_stack.model.Utente;
import com.example.Proggetto_final_f_stack.payloadDTO.request.LoginRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.request.UtenteRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.LoginResponse;
import com.example.Proggetto_final_f_stack.payloadDTO.response.UtenteResponse;
import com.example.Proggetto_final_f_stack.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<UtenteResponse> register(@Valid @RequestBody UtenteRequest utenteRequest) {
        if (utenteService.getUtenteByUsername(utenteRequest.getUsername()) != null) {
            return ResponseEntity.badRequest().body(null);
        }

        String encodedPassword = passwordEncoder.encode(utenteRequest.getPassword());

        Utente utente = new Utente(
            utenteRequest.getUsername(),
            encodedPassword,
            utenteRequest.getEmail(),
            List.of("ROLE_USER")
        );
        Utente savedUtente = utenteService.register(utente);
        UtenteResponse response = new UtenteResponse(savedUtente.getId(), savedUtente.getUsername(), savedUtente.getEmail(), savedUtente.getRoles());
        return ResponseEntity.created(null).body(response);
    }

    @PostMapping("/creaAdmin")
public ResponseEntity<UtenteResponse> creaUtenteAdmin(@Valid @RequestBody UtenteRequest utenteRequest) {
    if (utenteService.getUtenteByUsername(utenteRequest.getUsername()) != null) {
        return ResponseEntity.badRequest().body(null);
    }

    String encodedPassword = passwordEncoder.encode(utenteRequest.getPassword());

    Utente utente = new Utente(
        utenteRequest.getUsername(),
        encodedPassword,
        utenteRequest.getEmail(),
        List.of("ROLE_ADMIN") // Assegna automaticamente il ruolo ADMIN
    );
    Utente savedUtente = utenteService.register(utente);
    UtenteResponse response = new UtenteResponse(savedUtente.getId(), savedUtente.getUsername(), savedUtente.getEmail(), savedUtente.getRoles());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = utenteService.loginUtente(loginRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UtenteResponse> getUserByUsername(@PathVariable String username) {
        Utente utente = utenteService.getUtenteByUsername(username);
        if (utente != null) {
            UtenteResponse response = new UtenteResponse(utente.getId(), utente.getUsername(), utente.getEmail(), utente.getRoles());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UtenteResponse> updateUser(@PathVariable Long id, @RequestBody UtenteRequest utenteRequest) {
        String encodedPassword = passwordEncoder.encode(utenteRequest.getPassword());
        Utente utente = new Utente(utenteRequest.getUsername(), encodedPassword, utenteRequest.getEmail());
        Utente updatedUtente = utenteService.updateUtente(id, utente);
        if (updatedUtente != null) {
            UtenteResponse response = new UtenteResponse(updatedUtente.getId(), updatedUtente.getUsername(), updatedUtente.getEmail(), updatedUtente.getRoles());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
public ResponseEntity<List<UtenteResponse>> getAllUtenti() {
    List<UtenteResponse> utenti = utenteService.getAllUtenti();
    return ResponseEntity.ok(utenti);
}

@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/admins")
public ResponseEntity<List<UtenteResponse>> getAllAdmins() {
    List<UtenteResponse> admins = utenteService.getAllAdmins();
    return ResponseEntity.ok(admins);
}



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        utenteService.deleteUtente(id);
        return ResponseEntity.noContent().build();
    }
}
