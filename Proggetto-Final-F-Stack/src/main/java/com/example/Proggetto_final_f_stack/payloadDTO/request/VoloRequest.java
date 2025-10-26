package com.example.Proggetto_final_f_stack.payloadDTO.request;

import jakarta.validation.constraints.*;
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
    @Size(min = 2, max = 100, message = "La compagnia deve essere tra 2 e 100 caratteri")
    private String compagnia;

    @NotBlank(message = "L'origine non può essere vuota")
    @Size(min = 2, max = 100, message = "L'origine deve essere tra 2 e 100 caratteri")
    private String origine;

    @NotBlank(message = "La destinazione non può essere vuota")
    @Size(min = 2, max = 100, message = "La destinazione deve essere tra 2 e 100 caratteri")
    private String destinazione;

    @NotNull(message = "La data di partenza non può essere nulla")
    @Future(message = "La data di partenza deve essere nel futuro")
    private LocalDateTime dataPartenza;

    @NotNull(message = "La data di arrivo non può essere nulla")
    @Future(message = "La data di arrivo deve essere nel futuro")
    private LocalDateTime dataArrivo;

    @NotNull(message = "Il prezzo non può essere nullo")
    @DecimalMin(value = "0.01", message = "Il prezzo deve essere maggiore di 0")
    @DecimalMax(value = "100000.00", message = "Il prezzo non può superare 100.000")
    private BigDecimal prezzo;

    public VoloRequest(String nuovaCompagnia, String nuovaOrigine, String nuovaDestinazione, LocalDateTime now, LocalDateTime dataArrivo, double v) {
    }
}