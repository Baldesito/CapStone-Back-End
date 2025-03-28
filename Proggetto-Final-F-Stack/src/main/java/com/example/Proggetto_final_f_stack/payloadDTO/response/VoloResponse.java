package com.example.Proggetto_final_f_stack.payloadDTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoloResponse {
    private Long id;
    private String compagnia;
    private String origine;
    private String destinazione;
    private LocalDateTime dataPartenza;
    private LocalDateTime dataArrivo;
    private BigDecimal prezzo;

    // Costruttore con conversione da double a BigDecimal per il prezzo
    public VoloResponse(Long id, String compagniaAerea, String origine, String destinazione, LocalDateTime dataPartenza, LocalDateTime dataArrivo, double prezzo) {
        this.id = id;
        this.compagnia = compagniaAerea;
        this.origine = origine;
        this.destinazione = destinazione;
        this.dataPartenza = dataPartenza;
        this.dataArrivo = dataArrivo;
        this.prezzo = BigDecimal.valueOf(prezzo); // Converti double in BigDecimal
    }
}