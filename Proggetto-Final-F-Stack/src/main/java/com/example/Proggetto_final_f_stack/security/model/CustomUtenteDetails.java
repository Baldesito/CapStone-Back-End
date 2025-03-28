package com.example.Proggetto_final_f_stack.security.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.example.Proggetto_final_f_stack.model.Utente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUtenteDetails implements UserDetails {

    private final Utente utente;

    public CustomUtenteDetails(Utente utente) {
        this.utente = utente;
    }

   @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return utente.getRoles().stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }


    @Override
    public String getPassword() {
        return utente.getPassword();
    }

    @Override
    public String getUsername() {
        return utente.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return utente.getId();
    }
}
