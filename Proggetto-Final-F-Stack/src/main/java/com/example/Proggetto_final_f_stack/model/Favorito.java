package com.example.Proggetto_final_f_stack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favoriti")
public class Favorito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "volo_id", nullable = false)
    private Volo volo;

    // Costruttore per creare un favorito con Utente e Volo
    public Favorito(Utente utente, Volo volo) {
        this.utente = utente;
        this.volo = volo;
    }
}