package com.br.AdMon.service.mails;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.AdMon.models.TokensEmailVerification;

import com.br.AdMon.dao.TokenEmailVerificationDao;

@Service
public class ServiceTokenEmailVerification {

    @Autowired
    private TokenEmailVerificationDao tokensRepository;

    public void salvarToken(String token, String email) throws Exception{

        if(token.length() == 0){
            throw new Exception("Token inválido");
        }

        // Tempo de expiração do token
        LocalDateTime agora = LocalDateTime.now();

        // Salva o token junto com o e-mail do usuario
        TokensEmailVerification tokenObject = new TokensEmailVerification();
        tokenObject.setToken(token);
        tokenObject.setUserEmail(email);
        tokenObject.setExpireIn(agora.plusMinutes(15));

        // Salva o objeto no banco de dados
        tokensRepository.save(tokenObject);

    }

    public void apagarToken(String token, String email) throws Exception{

        if(tokensRepository.findByToken(token).size() == 0){
            throw new Exception("Este token não existe");
        }

        tokensRepository.deleteByToken(token, email);
    }

    public void apagarTokenExpirado() throws Exception{

        // Pega o horário atual
        LocalDateTime agora = LocalDateTime.now();

        // Busca todos os tokens
        List<TokensEmailVerification> tokens = tokensRepository.findAll();

        // Verifica todos os tokens se ainda são válidos, os que não forem serão deletados
        for(TokensEmailVerification tokenI : tokens){
            if(tokenI.getExpireIn().isAfter(agora)){
                tokensRepository.delete(tokenI);
            } else {
                System.out.println("O token " + tokenI.getToken() + " do usuário " + tokenI.getUserEmail() + " ainda é válido.");
            }
        }
    }
}
