package com.example.Proggetto_final_f_stack.service;

import com.example.Proggetto_final_f_stack.model.Passaggero;
import com.example.Proggetto_final_f_stack.payloadDTO.request.PassaggeroRequest;
import com.example.Proggetto_final_f_stack.repository.PassaggeroRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PassaggeroService {

        private final PassaggeroRepository passeggeroRepository;
    private final ObjectMapper objectMapper;

    public Passaggero salvaPasseggero(PassaggeroRequest dto) throws JsonProcessingException {
        Passaggero passeggero = Passaggero.builder()
            .nome(dto.getNome())
            .cognome(dto.getCognome())
            .nazionalita(dto.getNazionalita())
            .dataDiNascita(dto.getDataDiNascita())
            .genere(dto.getGenere())
            .bagagli(dto.getBagagli())
            .voloAndata(objectMapper.writeValueAsString(dto.getVoloAndata()))
            .voloRitorno(dto.getVoloRitorno() != null
                ? objectMapper.writeValueAsString(dto.getVoloRitorno())
                : null)
            .prezzo(dto.getPrezzo())
            .build();

        return passeggeroRepository.save(passeggero);
    }

}
