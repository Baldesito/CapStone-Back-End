package com.example.Proggetto_final_f_stack.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.Proggetto_final_f_stack.model.Utente;
import com.example.Proggetto_final_f_stack.payloadDTO.request.LoginRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.LoginResponse;
import com.example.Proggetto_final_f_stack.payloadDTO.response.UtenteResponse;
import com.example.Proggetto_final_f_stack.repository.UtenteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Transactional
    public Utente getUtenteById(Long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + id));
    }

    public Utente register(Utente utente) {
        // Hash della password prima del salvataggio
        if (utente.getPassword() != null && !utente.getPassword().startsWith("$2a$")) {
            utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        }
        return utenteRepository.save(utente);
    }

    @Transactional
    public Utente updateUtente(Long id, Utente utente) {
        Utente utenteEsistente = getUtenteById(id);
        utenteEsistente.setUsername(utente.getUsername());

        // Hash della password solo se è stata modificata e non è già hashata
        if (utente.getPassword() != null && !utente.getPassword().isEmpty()) {
            if (!utente.getPassword().startsWith("$2a$")) {
                utenteEsistente.setPassword(passwordEncoder.encode(utente.getPassword()));
            } else {
                utenteEsistente.setPassword(utente.getPassword());
            }
        }

        utenteEsistente.setEmail(utente.getEmail());
        utenteEsistente.setRoles(utente.getRoles());
        return utenteRepository.save(utenteEsistente);
    }

    @Transactional
    public void deleteUtente(Long id) {
        utenteRepository.deleteById(id);
    }

    public List<UtenteResponse> getAllUtenti() {
        List<Utente> utenti = utenteRepository.findByRolesNotContaining("ROLE_ADMIN");
        return utenti.stream()
                .map(u -> new UtenteResponse(u.getId(), u.getUsername(), u.getEmail(), u.getRoles()))
                .collect(Collectors.toList());
    }

    public List<UtenteResponse> getAllAdmins() {
        List<Utente> admins = utenteRepository.findByRolesContaining("ROLE_ADMIN");
        return admins.stream()
                .map(u -> new UtenteResponse(u.getId(), u.getUsername(), u.getEmail(), u.getRoles()))
                .collect(Collectors.toList());
    }

    // ✅ Modificato per cercare l'utente tramite EMAIL invece di username
    public LoginResponse loginUtente(LoginRequest loginRequest) {
        Utente utente = utenteRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Utente non trovato con email: " + loginRequest.getEmail()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), utente.getPassword())) {
            throw new RuntimeException("Password errata");
        }

        String token = generateToken(utente);
        String role = utente.getRoles() != null && !utente.getRoles().isEmpty() ? utente.getRoles().get(0) : "ROLE_USER";
        return new LoginResponse(utente.getId(), utente.getUsername(), utente.getEmail(), role, "Login effettuato con successo", token);
    }

    private String generateToken(Utente utente) {
        return JWT.create()
                .withSubject(utente.getEmail()) // Usa email invece di username
                .withClaim("id", utente.getId())
                .withClaim("username", utente.getUsername())
                .withClaim("roles", utente.getRoles()) // Assicurati che i ruoli siano inclusi
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration))
                .sign(Algorithm.HMAC256(jwtSecret.getBytes()));
    }

    private UtenteResponse convertToUtenteResponse(Utente utente) {
        UtenteResponse response = new UtenteResponse();
        response.setId(utente.getId());
        response.setUsername(utente.getUsername());
        response.setEmail(utente.getEmail());
        response.setRuoli(utente.getRoles());
        return response;
    }
        @Transactional
    public Utente getUtenteByUsername(String username) {
        Optional<Utente> utente = utenteRepository.findByUsername(username);
        return utente.orElse(null);
    }


}
