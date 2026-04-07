package com.example.Proggetto_final_f_stack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void inviaEmailConferma(String emailDestinatario, String nomePasseggero, String tratta) {
        SimpleMailMessage messaggio = new SimpleMailMessage();
        messaggio.setTo(emailDestinatario);
        messaggio.setSubject("Conferma Prenotazione - Baldesito-Fly");
        messaggio.setText("Gentile " + nomePasseggero + ",\n\n" +
                "Il tuo biglietto per la tratta " + tratta + " è stato emesso correttamente.\n" +
                "Grazie per aver scelto Baldesito-Fly!\n\n" +
                "Buon viaggio!");

        mailSender.send(messaggio);
    }
}