package com.example.Proggetto_final_f_stack.payloadDTO.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContattoResponse {
    private Long id;
    private String nome;
    private String cognome;
    private String indirizzo;
    private String citta;
    private String codicePostale;
    private String nazione;
    private String prefisso;
    private String telefono;
    private String email;

    public ContattoResponse(Long id, String nome, String cognome, String indirizzo, String citta,
                            String codicePostale, String nazione, String prefisso,
                            String telefono, String email) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.codicePostale = codicePostale;
        this.nazione = nazione;
        this.prefisso = prefisso;
        this.telefono = telefono;
        this.email = email;

    }
}
