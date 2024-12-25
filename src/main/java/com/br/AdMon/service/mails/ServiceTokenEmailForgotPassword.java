package com.br.AdMon.service.mails;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.AdMon.dao.TokenForgotPasswordDao;
import com.br.AdMon.models.TokensForgotPassword;

@Service
public class ServiceTokenEmailForgotPassword {

    @Autowired
    private TokenForgotPasswordDao tokensForgotPasswordRepository;

    public Boolean verifyToken(String token) throws Exception {

        // Valida o token
        if (tokensForgotPasswordRepository.findByToken(token) == null) {
            throw new Exception("Token inválido");
        }

        // Recupera dados que o token tem
        TokensForgotPassword tokenObject = tokensForgotPasswordRepository.findByToken(token);

        // Verifica se o token expirou
        if (tokenObject.getExpireIn().isBefore(LocalDateTime.now())) {
            tokensForgotPasswordRepository.delete(tokenObject);
            throw new Exception("Token expirado");
        }

        return true;
    }
}
