package com.example.Proggetto_final_f_stack.payloadDTO.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoRequest {
    private String numeroCarta; // Mascherare lato frontend
    private String titolareCarta;
    private String dataScadenza; // MM/YY
    private BigDecimal importo;
    private Long utenteId; // Per collegare il pagamento all'utente
}
