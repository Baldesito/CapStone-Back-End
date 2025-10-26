package com.example.Proggetto_final_f_stack.controller;

import com.example.Proggetto_final_f_stack.payloadDTO.request.LoginRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.LoginResponse;
import com.example.Proggetto_final_f_stack.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UtenteService utenteService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUtente(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = utenteService.loginUtente(loginRequest);
        return ResponseEntity.ok(response);
    }
}