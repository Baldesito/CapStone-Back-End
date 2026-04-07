package com.example.Proggetto_final_f_stack.payloadDTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoritoResponse {
    private Long id;
    private Long utenteId;
    private Long voloId;
    private String messaggio;

    // Info aggiuntive sul volo
    private String compagnia;
    private String origine;
    private String destinazione;
    private LocalDateTime dataPartenza;
    private LocalDateTime dataArrivo;
    private BigDecimal prezzo;
    private String durata; // <-- nuova proprietà per durata (es. "PT1H30M")

    public FavoritoResponse(Long id, Long utenteId, Long voloId) {
        this.id = id;
        this.utenteId = utenteId;
        this.voloId = voloId;
    }

    public FavoritoResponse(String messaggio) {
        this.messaggio = messaggio;
    }

    public FavoritoResponse(Long id, Long utenteId, Long voloId, String compagnia, String origine,
                            String destinazione, LocalDateTime dataPartenza, LocalDateTime dataArrivo,
                            BigDecimal prezzo, String durata, String messaggio) {
        this.id = id;
        this.utenteId = utenteId;
        this.voloId = voloId;
        this.compagnia = compagnia;
        this.origine = origine;
        this.destinazione = destinazione;
        this.dataPartenza = dataPartenza;
        this.dataArrivo = dataArrivo;
        this.prezzo = prezzo;
        this.durata = durata;
        this.messaggio = messaggio;
    }
}
