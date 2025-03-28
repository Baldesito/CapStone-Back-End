package com.example.Proggetto_final_f_stack.controller;

import com.example.Proggetto_final_f_stack.payloadDTO.request.ContattoRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.ContattoResponse;
import com.example.Proggetto_final_f_stack.service.ContattoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contatti")
@CrossOrigin(origins = "http://localhost:3000")
public class ContattoController {

    @Autowired
    private ContattoService contattoService;

    @PostMapping("/salva")
    public ResponseEntity<ContattoResponse> salvaContatto(@RequestBody ContattoRequest contattoRequest) {
        ContattoResponse contattoResponse = contattoService.salvaContatto(contattoRequest);
        return ResponseEntity.ok(contattoResponse);
    }
}
