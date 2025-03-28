package com.example.Proggetto_final_f_stack.payloadDTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazioneResponse {

    private Long id;

    private LocalDateTime dataPrenotazione;

    private Long utenteId;

    private Long voloId;
}