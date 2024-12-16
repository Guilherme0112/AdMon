package com.br.AdMon.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.dao.TokensDao;
import com.br.AdMon.dao.UsuarioDao;
import com.br.AdMon.models.Tokens;
import com.br.AdMon.service.ServiceToken;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmailController {

    @Autowired
    private UsuarioDao usuarioRepository; 

    @Autowired
    private ServiceToken tokenService;

    @Autowired
    private TokensDao tokenRepository;

    @GetMapping("/verify-email/{token}")
    public ModelAndView VerifyEmail(@PathVariable("token") String token, HttpSession http) throws Exception{

        ModelAndView mv = new ModelAndView();

        try {

            // Verifica se o token existe no banco de dados
            List<Tokens> tokenBD = tokenRepository.findByToken(token);
            if(tokenBD.size() == 0){
                throw new Exception("Token inválido");
            }
            
            // Obtém o e-mail do usuário inativo
            Tokens tokenObject = (Tokens) tokenBD.get(0);

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
            mv.addObject("erro_exception", "Ocorreu um erro. Tente novamente mais tarde.");      
            mv.setViewName("mails/confirm-email");
        }

        return mv;
    }

    @GetMapping("/sended-email")
    public ModelAndView EmailEnviado(){

        ModelAndView mv = new ModelAndView();

        mv.setViewName("mails/sended-email");

        return mv;
    }
}
