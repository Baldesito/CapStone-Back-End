package com.example.Proggetto_final_f_stack.controller;


import com.example.Proggetto_final_f_stack.model.Pagamento;
import com.example.Proggetto_final_f_stack.payloadDTO.request.PagamentoRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.PagamentoResponse;
import com.example.Proggetto_final_f_stack.repository.PagamentoRepository;
import com.example.Proggetto_final_f_stack.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamenti")
public class PagamentoController {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PagamentoService pagamentoService;

    // Effettua un pagamento (endpoint sicuro con validazione)
    @PostMapping("/effettua")
    public ResponseEntity<PagamentoResponse> effettuaPagamento(@Valid @RequestBody PagamentoRequest request) {
        PagamentoResponse response = pagamentoService.effettuaPagamento(request);
        return ResponseEntity.ok(response);
    }

    // Salva un pagamento (mantenuto per retrocompatibilità)
    @PostMapping("/salva")
    public Pagamento salvaPagamento(@RequestBody Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    // Recupera tutti i pagamenti
    @GetMapping("/tutti")
    public List<Pagamento> getTuttiPagamenti() {
        return pagamentoRepository.findAll();
    }
}


