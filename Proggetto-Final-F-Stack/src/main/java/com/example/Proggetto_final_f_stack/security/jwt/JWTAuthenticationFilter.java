package com.example.Proggetto_final_f_stack.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.Proggetto_final_f_stack.payloadDTO.request.LoginRequest;
import com.example.Proggetto_final_f_stack.security.model.CustomUtenteDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;
    private final String jwtSecret;
    private final long jwtExpiration;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, String jwtSecret, long jwtExpiration) {
        this.authenticationManager = authenticationManager;
        this.jwtSecret = jwtSecret;
        this.jwtExpiration = jwtExpiration;
        setFilterProcessesUrl("/api/auth/login"); // Imposta l'URL per il login
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginRequest credentials = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            String email = credentials.getEmail();
            String password = credentials.getPassword();



            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (IOException e) {
            logger.error("Errore durante la lettura delle credenziali", e);
            throw new RuntimeException("Credenziali non valide", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String username = authResult.getName();
        List<String> roles = authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        logger.info("Autenticazione riuscita per l'utente: {}", username);

        String token = JWT.create()
                .withSubject(username)
                .withClaim("id", ((CustomUtenteDetails) authResult.getPrincipal()).getId())
                .withClaim("username", username)
                .withClaim("roles", roles)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration))
                .sign(Algorithm.HMAC256(jwtSecret.getBytes()));

        // Costruisci una risposta JSON completa
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("id", ((CustomUtenteDetails) authResult.getPrincipal()).getId());
        responseBody.put("username", username);
        responseBody.put("role", roles.isEmpty() ? "ROLE_USER" : roles.get(0));
        responseBody.put("message", "Login effettuato con successo");
        responseBody.put("token", token);

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        logger.error("Autenticazione fallita: {}", failed.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Autenticazione fallita: " + failed.getMessage());

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
    }
}