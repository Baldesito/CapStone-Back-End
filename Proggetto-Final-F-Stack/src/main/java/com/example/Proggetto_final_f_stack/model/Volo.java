package com.example.Proggetto_final_f_stack.model;

import com.example.Proggetto_final_f_stack.payloadDTO.request.VoloRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "voli")
public class Volo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String compagnia;
    private String origine;
    private String destinazione;
    private LocalDateTime dataPartenza;
    private LocalDateTime dataArrivo;
    private BigDecimal prezzo;

    @OneToMany(mappedBy = "volo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Prenotazione> prenotazioni;

    @OneToMany(mappedBy = "volo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Favorito> preferiti;


    // Costruttore per creare un volo senza ID (per nuove entità)
    public Volo(String compagnia, String origine, String destinazione, LocalDateTime dataPartenza, LocalDateTime dataArrivo, BigDecimal prezzo) {
        this.compagnia = compagnia;
        this.origine = origine;
        this.destinazione = destinazione;
        this.dataPartenza = dataPartenza;
        this.dataArrivo = dataArrivo;
        this.prezzo = prezzo;
    }

    // Costruttore per creare un volo a partire da un VoloRequest
    public Volo(VoloRequest voloRequest) {
        this.compagnia = voloRequest.getCompagnia();
        this.origine = voloRequest.getOrigine();
        this.destinazione = voloRequest.getDestinazione();
        this.dataPartenza = voloRequest.getDataPartenza();
        this.dataArrivo = voloRequest.getDataArrivo();
        this.prezzo = voloRequest.getPrezzo();
    }

    // Costruttore per creare un volo con ID (utile per test o mapping manuale)
    public Volo(Long id, String compagnia, String origine, String destinazione, LocalDateTime dataPartenza, LocalDateTime dataArrivo, BigDecimal prezzo) {
        this.id = id;
        this.compagnia = compagnia;
        this.origine = origine;
        this.destinazione = destinazione;
        this.dataPartenza = dataPartenza;
        this.dataArrivo = dataArrivo;
        this.prezzo = prezzo;
    }

}