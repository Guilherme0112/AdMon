package com.br.AdMon.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.Util.JWTUtil;
import com.br.AdMon.dao.UsuarioDao;
import com.br.AdMon.models.Usuarios;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpSession;

@Controller
public class EmailController {

    @Autowired
    private UsuarioDao usuarioRepository; 

    @GetMapping("/verify-email/{token}")
    public ModelAndView VerifyEmail(@PathVariable("token") String token, HttpSession http) throws JwtException, Exception{

        ModelAndView mv = new ModelAndView();

        try {

            // Verifica se é valido
            if(JWTUtil.extractData(token) == null){
                throw new JwtException("Token inválido");
            }
            
            // Verifica se está expirado
            if(JWTUtil.isTokenExpired(token)){
                throw new JwtException("Token expirado");
            }

            // Obtém os dados do token
            Map<String, Object> data = JWTUtil.extractData(token);

            // Pega o e-mail do token
            String email = (String) data.get("email");

            Usuarios emailInative = usuarioRepository.findByEmailInactive(email);

            // Se não existir o e-mail, ele retorna o erro
            if(emailInative == null){
                throw new Exception("Erro ao registrar a conta. Tente novamente mais tarde.");
            }

            // Atualiza a conta como ativa 
            usuarioRepository.updateAtivo(email, true);

            mv.setViewName("redirect:/auth/login");

        } catch (JwtException e) {

            System.out.println("Erro:" + e);
            mv.addObject("erro_jwt", e.getMessage());
            mv.setViewName("mails/confirm-email");

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
