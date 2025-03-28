package com.example.Proggetto_final_f_stack.model;

import com.example.Proggetto_final_f_stack.enumerated.StatoPagamento;
import jakarta.persistence.*;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroCarta;  // Solo cifre mascherate per sicurezza
    private String titolareCarta;
    private String dataScadenza; // MM/YY formato stringa
    private BigDecimal importo;
    private LocalDate dataPagamento;

    @Enumerated(EnumType.STRING)
    private StatoPagamento stato; // ENUM per indicare se è riuscito o meno

    @ManyToOne
    @JoinColumn(name = "utente_id") // Relazione con l'utente che effettua il pagamento
    private Utente utente;

    public Pagamento() {
        this.dataPagamento = LocalDate.now();
    }
}

