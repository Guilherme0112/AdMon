package com.br.AdMon.service.mails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class ServiceEmail {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String destinatario, String assunto, String mensagem) throws Exception{

        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(mensagem, true);
            helper.setFrom("guimendesmen124@gmail.com");

            mailSender.send(message);

            System.out.println("E-mail enviado com sucesso!");
        } catch (Exception e){

            throw new Exception("Erro ao enviar email ", e);
        }
    }
}
