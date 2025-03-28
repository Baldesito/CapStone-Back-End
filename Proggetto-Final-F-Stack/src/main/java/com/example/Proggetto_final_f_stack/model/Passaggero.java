package com.example.Proggetto_final_f_stack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "passeggeri")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passaggero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cognome;
    private String nazionalita;
    private LocalDate dataDiNascita;
    private String genere;

    @Embedded
    private Bagagli bagagli;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String voloAndata;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String voloRitorno;

    private BigDecimal prezzo;
}

