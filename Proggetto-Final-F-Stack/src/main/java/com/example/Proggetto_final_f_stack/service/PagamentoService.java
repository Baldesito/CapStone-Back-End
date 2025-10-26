package com.example.Proggetto_final_f_stack.service;


import com.example.Proggetto_final_f_stack.enumerated.StatoPagamento;
import com.example.Proggetto_final_f_stack.model.Pagamento;
import com.example.Proggetto_final_f_stack.model.Utente;
import com.example.Proggetto_final_f_stack.payloadDTO.request.PagamentoRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.PagamentoResponse;
import com.example.Proggetto_final_f_stack.repository.PagamentoRepository;
import com.example.Proggetto_final_f_stack.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    public PagamentoResponse effettuaPagamento(PagamentoRequest request) {
        Optional<Utente> utenteOpt = utenteRepository.findById(request.getUtenteId());

        if (utenteOpt.isEmpty()) {
            throw new RuntimeException("Utente non trovato.");
        }

        // Mascheriamo il numero della carta per la sicurezza
        // La validazione del formato è già gestita dalla @Pattern in PagamentoRequest
        String ultimeCifre = request.getNumeroCarta().length() >= 4
            ? request.getNumeroCarta().substring(request.getNumeroCarta().length() - 4)
            : request.getNumeroCarta();
        String numeroCartaMascherato = "**** **** **** " + ultimeCifre;

        Pagamento pagamento = new Pagamento();
        pagamento.setNumeroCarta(numeroCartaMascherato);
        pagamento.setTitolareCarta(request.getTitolareCarta());
        pagamento.setDataScadenza(request.getDataScadenza());
        pagamento.setImporto(request.getImporto());
        pagamento.setUtente(utenteOpt.get());
        pagamento.setStato(StatoPagamento.COMPLETATO);

        pagamentoRepository.save(pagamento);

        return new PagamentoResponse(
                pagamento.getId(),
                numeroCartaMascherato,
                pagamento.getTitolareCarta(),
                pagamento.getDataScadenza(),
                pagamento.getImporto(),
                pagamento.getDataPagamento(),
                pagamento.getStato()
        );
    }
}

