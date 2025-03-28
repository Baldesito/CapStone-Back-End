package com.example.Proggetto_final_f_stack.payloadDTO.response;


import com.example.Proggetto_final_f_stack.enumerated.StatoPagamento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PagamentoResponse {
    private Long id;
    private String numeroCarta; // Solo ultime 4 cifre per sicurezza
    private String titolareCarta;
    private String dataScadenza;
    private BigDecimal importo;
    private LocalDate dataPagamento;
    private StatoPagamento statoPagamento;

    public PagamentoResponse(Long id, String numeroCarta, String titolareCarta,
                             String dataScadenza, BigDecimal importo,
                             LocalDate dataPagamento, StatoPagamento statoPagamento) {
        this.id = id;
        this.numeroCarta = numeroCarta;
        this.titolareCarta = titolareCarta;
        this.dataScadenza = dataScadenza;
        this.importo = importo;
        this.dataPagamento = dataPagamento;
        this.statoPagamento = statoPagamento;
    }
}
