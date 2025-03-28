package com.example.Proggetto_final_f_stack.payloadDTO.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoritoRequest {
    @NotNull(message = "L'ID dell'utente non può essere nullo")
    private Long utenteId;

    @NotNull(message = "L'ID del volo non può essere nullo")
    private Long voloId;
}