package com.example.Proggetto_final_f_stack.payloadDTO.request;

import lombok.Data;

@Data
public class ContattoRequest {
    private String nome;
    private String cognome;
    private String indirizzo;
    private String citta;
    private String codicePostale;
    private String nazione;
    private String prefisso;
    private String telefono;
    private String email;
}
