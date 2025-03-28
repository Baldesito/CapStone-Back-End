package com.example.Proggetto_final_f_stack.security;

import com.example.Proggetto_final_f_stack.repository.UtenteRepository;
import com.example.Proggetto_final_f_stack.security.jwt.JWTAuthenticationFilter;
import com.example.Proggetto_final_f_stack.security.jwt.JWTAuthorizationFilter;
import com.example.Proggetto_final_f_stack.security.model.CustomUtenteDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final UtenteRepository utenteRepository;
    private final String jwtSecret;
    private final long jwtExpiration;

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    public SecurityConfig(UtenteRepository utenteRepository,
                          @Value("${jwt.secret}") String jwtSecret,
                          @Value("${jwt.expiration}") long jwtExpiration) {
        this.utenteRepository = utenteRepository;
        this.jwtSecret = jwtSecret;
        this.jwtExpiration = jwtExpiration;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager, jwtSecret, jwtExpiration);
        JWTAuthorizationFilter jwtAuthorizationFilter = new JWTAuthorizationFilter(authenticationManager, jwtSecret);

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ Abilita correttamente i CORS
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/utenti/register", "/api/utenti/login").permitAll() // ✅ Login e registrazione liberi
                .requestMatchers("/api/voli/**").permitAll()
                    .requestMatchers("/api/voli/crea/**").permitAll()
                    .requestMatchers("/api/contatti/salva/**").authenticated()
                    .requestMatchers("/api/contatti/**").permitAll()
                    .requestMatchers("/api/passaggeri/**").authenticated()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/user/**").hasRole("USER")
                .requestMatchers("/api/prenotazioni/**").authenticated()
                .requestMatchers("/api/preferiti/**").authenticated()
                    .requestMatchers("/api/pagamenti/salva/**").authenticated()
                    .requestMatchers("/api/pagamenti/**").permitAll()


                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        logger.info("Configurazione di sicurezza applicata");
        return http.build();
    }

    // ✅ CORRETTA configurazione dei CORS come `CorsConfigurationSource`
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173"); // ✅ Permetti chiamate dal frontend React
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source; // ✅ Ora è un `CorsConfigurationSource`, non un `CorsFilter`
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(List.of(authProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> utenteRepository.findByUsername(username)
            .map(CustomUtenteDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));
    }
}
