package com.example.Proggetto_final_f_stack.controller;


import com.example.Proggetto_final_f_stack.model.Pagamento;
import com.example.Proggetto_final_f_stack.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamenti")
@CrossOrigin(origins = "http://localhost:3000")
public class PagamentoController {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    // Salva un pagamento
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


