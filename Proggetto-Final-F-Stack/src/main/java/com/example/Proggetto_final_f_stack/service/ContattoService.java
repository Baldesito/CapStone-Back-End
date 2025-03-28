package com.example.Proggetto_final_f_stack.service;


import com.example.Proggetto_final_f_stack.model.Contatto;
import com.example.Proggetto_final_f_stack.payloadDTO.request.ContattoRequest;
import com.example.Proggetto_final_f_stack.payloadDTO.response.ContattoResponse;
import com.example.Proggetto_final_f_stack.repository.ContattoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContattoService {

    @Autowired
    private ContattoRepository contattoRepository;

    public ContattoResponse salvaContatto(ContattoRequest contattoRequest) {
        Contatto contatto = new Contatto();
        contatto.setNome(contattoRequest.getNome());
        contatto.setCognome(contattoRequest.getCognome());
        contatto.setIndirizzo(contattoRequest.getIndirizzo());
        contatto.setCitta(contattoRequest.getCitta());
        contatto.setCodicePostale(contattoRequest.getCodicePostale());
        contatto.setNazione(contattoRequest.getNazione());
        contatto.setPrefisso(contattoRequest.getPrefisso());
        contatto.setTelefono(contattoRequest.getTelefono());
        contatto.setEmail(contattoRequest.getEmail());

        Contatto contattoSalvato = contattoRepository.save(contatto);

        return new ContattoResponse(
                contattoSalvato.getId(),
                contattoSalvato.getNome(),
                contattoSalvato.getCognome(),
                contattoSalvato.getIndirizzo(),
                contattoSalvato.getCitta(),
                contattoSalvato.getCodicePostale(),
                contattoSalvato.getNazione(),
                contattoSalvato.getPrefisso(),
                contattoSalvato.getTelefono(),
                contattoSalvato.getEmail()
        );
    }
}
