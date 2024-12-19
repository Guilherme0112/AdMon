package com.br.AdMon.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.dao.TokenEmailVerificationDao;
import com.br.AdMon.dao.UsuarioDao;
import com.br.AdMon.models.TokensEmailVerification;
import com.br.AdMon.service.mails.ServiceTokenEmailVerification;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmailController {

    @Autowired
    private UsuarioDao usuarioRepository; 

    @Autowired
    private ServiceTokenEmailVerification tokenService;

    @Autowired
    private TokenEmailVerificationDao tokenRepository;

    // Verifica se o e-mail existe
    // Processo feito quando se cria uma conta
    @GetMapping("/verify-email/{token}")
    public ModelAndView VerifyEmail(@PathVariable("token") String token, HttpSession http) throws Exception{

        ModelAndView mv = new ModelAndView();

        try {

            // Verifica se o token existe no banco de dados
            List<TokensEmailVerification> tokenBD = tokenRepository.findByToken(token);
            if(tokenBD.size() == 0){
                throw new Exception("Token inválido");
            }
            
            // Obtém o e-mail do usuário inativo
            TokensEmailVerification tokenObject = (TokensEmailVerification) tokenBD.get(0);

            // Verifica se a conta está inativa
            if(usuarioRepository.findByEmailInactive(tokenObject.getUserEmail()) == null){
                throw new Exception("Este e-mail não está registrado ou já está ativo");
            }

            // Atualiza a conta como ativa 
            usuarioRepository.updateAtivo(tokenObject.getUserEmail(), true);

            // Apaga o token do email
            tokenService.apagarToken(token, tokenObject.getUserEmail());

            // Verificação dos tokens do banco de dados
            // Deleta os tokens expirados
            tokenService.apagarTokenExpirado();

            mv.setViewName("redirect:/auth/login");

        } catch (Exception e){

            System.out.println("Erro: " + e);      
            mv.addObject("erro_exception", e.getMessage());      
            mv.setViewName("mails/confirm-email");
        }

        return mv;
    }

    // Substituí a senha
    // Processo feito na horade REDEFINIR a senha
    @GetMapping("/forgot-password/{token}")
    public ModelAndView ForgotPassword(@PathVariable("token") String tokenPassword) throws Exception{

        ModelAndView mv = new ModelAndView();

        return mv;
    }
}
