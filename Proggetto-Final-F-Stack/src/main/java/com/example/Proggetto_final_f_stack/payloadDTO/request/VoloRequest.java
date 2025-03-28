package com.example.Proggetto_final_f_stack.payloadDTO.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoloRequest {
    @NotBlank(message = "La compagnia non può essere vuota")
    private String compagnia;

    @NotBlank(message = "L'origine non può essere vuota")
    private String origine;

    @NotBlank(message = "La destinazione non può essere vuota")
    private String destinazione;

    @NotNull(message = "La data di partenza non può essere nulla")
    private LocalDateTime dataPartenza;

    @NotNull(message = "La data di arrivo non può essere nulla")
    private LocalDateTime dataArrivo;

    @NotNull(message = "Il prezzo non può essere nullo")
    @DecimalMin(value = "0.0", inclusive = false, message = "Il prezzo deve essere maggiore di 0")
    private BigDecimal prezzo;

    public VoloRequest(String nuovaCompagnia, String nuovaOrigine, String nuovaDestinazione, LocalDateTime now, LocalDateTime dataArrivo, double v) {
    }
}