package com.br.AdMon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ServiceEmail {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String destinatario, String assunto, String mensagem) throws Exception{

        SimpleMailMessage email = new SimpleMailMessage();

        try{

            email.setTo(destinatario);
            email.setSubject(assunto);
            email.setText(mensagem);
            email.setFrom("guimendesmen124@gmail.com");

            mailSender.send(email);

        } catch (Exception e){

            throw new Exception("Erro ao enviar email: ", e);
        }
    }
}
