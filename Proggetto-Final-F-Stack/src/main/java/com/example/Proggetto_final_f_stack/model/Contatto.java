package com.example.Proggetto_final_f_stack.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contatti")
public class Contatto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
