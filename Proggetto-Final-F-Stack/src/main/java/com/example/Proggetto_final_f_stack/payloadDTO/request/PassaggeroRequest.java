package com.example.Proggetto_final_f_stack.payloadDTO.request;

import com.example.Proggetto_final_f_stack.model.Bagagli;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PassaggeroRequest {

    private String nome;
    private String cognome;
    private String nazionalita;
    private LocalDate dataDiNascita;
    private String genere;

    private Bagagli bagagli;

    private Object voloAndata;
    private Object voloRitorno;

    private BigDecimal prezzo;
}
