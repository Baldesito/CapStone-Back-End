package com.example.Proggetto_final_f_stack.payloadDTO.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private Long id;
    private String username;
    private String email;
    private String role;
    private String message;
    private String token;


}