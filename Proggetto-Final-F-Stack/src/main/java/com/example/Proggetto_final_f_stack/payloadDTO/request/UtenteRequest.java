package com.example.Proggetto_final_f_stack.payloadDTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtenteRequest {

    @NotBlank(message = "L'username non può essere vuoto")
    @Size(min = 3, max = 50, message = "L'username deve essere tra 3 e 50 caratteri")
    private String username;

    @NotBlank(message = "La password non può essere vuota")
    @Size(min = 8, message = "La password deve contenere almeno 8 caratteri")
    @jakarta.validation.constraints.Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "La password deve contenere almeno una lettera maiuscola, una minuscola, un numero e un carattere speciale (@$!%*?&)"
    )
    private String password;

    @NotBlank(message = "L'email non può essere vuota")
    @Email(message = "L'email deve essere valida")
    private String email;
}