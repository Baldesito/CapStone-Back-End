package com.example.Proggetto_final_f_stack.payloadDTO.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazioneRequest {

    @NotNull(message = "La data di prenotazione non può essere nulla")
    @FutureOrPresent(message = "La data di prenotazione non può essere nel passato")
    private LocalDateTime dataPrenotazione;

    @NotNull(message = "L'ID dell'utente non può essere nullo")
    private Long utenteId;

    @NotNull(message = "L'ID del volo non può essere nullo")
    private Long voloId;
}