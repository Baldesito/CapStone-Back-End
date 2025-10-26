package com.example.Proggetto_final_f_stack.payloadDTO.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoRequest {

    @NotBlank(message = "Il numero della carta non può essere vuoto")
    @Pattern(regexp = "^[0-9]{16}$", message = "Il numero della carta deve contenere esattamente 16 cifre")
    private String numeroCarta; // Mascherare lato frontend

    @NotBlank(message = "Il titolare della carta non può essere vuoto")
    @Size(min = 2, max = 100, message = "Il nome del titolare deve essere tra 2 e 100 caratteri")
    private String titolareCarta;

    @NotBlank(message = "La data di scadenza non può essere vuota")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/[0-9]{2}$", message = "La data di scadenza deve essere nel formato MM/YY")
    private String dataScadenza; // MM/YY

    @NotNull(message = "L'importo non può essere nullo")
    @DecimalMin(value = "0.01", message = "L'importo deve essere maggiore di zero")
    @DecimalMax(value = "100000.00", message = "L'importo non può superare 100.000")
    private BigDecimal importo;

    @NotNull(message = "L'ID utente non può essere nullo")
    @Positive(message = "L'ID utente deve essere positivo")
    private Long utenteId; // Per collegare il pagamento all'utente
}
