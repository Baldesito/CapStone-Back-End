package com.example.Proggetto_final_f_stack.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bagagli {
   private String bagaglioAManoAndata;
    private String bagaglioAManoRitorno;
    private String bagaglioInStiva;
}
