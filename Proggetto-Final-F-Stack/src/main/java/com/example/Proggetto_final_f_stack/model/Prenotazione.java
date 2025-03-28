package com.example.Proggetto_final_f_stack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prenotazioni")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Evita problemi con Hibernate
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_prenotazione", nullable = false)
    private LocalDateTime dataPrenotazione;

    @ManyToOne(fetch = FetchType.LAZY) // Cambia a LAZY per evitare serializzazione ricorsiva
    @JoinColumn(name = "utente_id", nullable = false)
    @JsonIgnore // Evita che venga serializzato in JSON
    private Utente utente;

    @ManyToOne(fetch = FetchType.LAZY) // Cambia a LAZY
    @JoinColumn(name = "volo_id", nullable = false)
    @JsonIgnore
    private Volo volo;

    public Prenotazione(LocalDateTime dataPrenotazione, Utente utente, Volo volo) {
        this.dataPrenotazione = dataPrenotazione;
        this.utente = utente;
        this.volo = volo;
    }
}
