package com.example.Proggetto_final_f_stack.payloadDTO.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazioneRequest {

    @NotNull(message = "La data di prenotazione non può essere nulla")
    @FutureOrPresent(message = "La data di prenotazione non può essere nel passato")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataPrenotazione;

    @NotNull(message = "L'ID dell'utente non può essere nullo")
    @Positive(message = "L'ID dell'utente deve essere positivo")
    private Long utenteId;

    @NotNull(message = "L'ID del volo non può essere nullo")
    @Positive(message = "L'ID del volo deve essere positivo")
    private Long voloId;
}