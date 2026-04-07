package com.example.Proggetto_final_f_stack.payloadDTO.response;

import com.example.Proggetto_final_f_stack.model.Prenotazione;
import com.example.Proggetto_final_f_stack.model.Volo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazioneResponse {

    private Long id;
    private LocalDateTime dataPrenotazione;
    private Long utenteId;
    private Long voloId;

    private String origine;
    private String destinazione;
    private LocalDateTime dataPartenza;
    private LocalDateTime dataArrivo;
    private String compagnia;
    private BigDecimal prezzo;

    // COSTRUTTORE INTELLIGENTE: Mappa tutti i dati del volo in automatico!
    public PrenotazioneResponse(Prenotazione p) {
        this.id = p.getId();
        this.dataPrenotazione = p.getDataPrenotazione();
        this.utenteId = p.getUtente() != null ? p.getUtente().getId() : null;

        if (p.getVolo() != null) {
            Volo v = p.getVolo();
            this.voloId = v.getId();
            this.origine = v.getOrigine();
            this.destinazione = v.getDestinazione();
            this.dataPartenza = v.getDataPartenza();
            this.dataArrivo = v.getDataArrivo();
            this.compagnia = v.getCompagnia();
            this.prezzo = v.getPrezzo();
        }
    }
}