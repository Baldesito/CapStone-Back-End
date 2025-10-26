package com.example.Proggetto_final_f_stack.controller;


import com.example.Proggetto_final_f_stack.model.Passaggero;
import com.example.Proggetto_final_f_stack.payloadDTO.request.PassaggeroRequest;
import com.example.Proggetto_final_f_stack.service.PassaggeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/passaggeri")
@RequiredArgsConstructor
public class PassaggeroController {

     private final PassaggeroService passeggeroService;

    @PostMapping("/crea")
    public ResponseEntity<?> creaPasseggero(@RequestBody PassaggeroRequest dto) {
        try {
            Passaggero passeggeroSalvato = passeggeroService.salvaPasseggero(dto);
            return ResponseEntity.ok(passeggeroSalvato);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Errore salvataggio passeggero: " + e.getMessage());
        }
    }
}
