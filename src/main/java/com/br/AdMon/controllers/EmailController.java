package com.br.AdMon.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.Util.JWTUtil;
import com.br.AdMon.models.Usuarios;
import com.br.AdMon.service.ServiceUsuario;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpSession;

@Controller
public class EmailController {

    @Autowired
    private ServiceUsuario usuarioService;

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

            String email = (String) data.get("email");
            String nome = (String) data.get("nome");
            String senha = (String) data.get("senha");

            // Cria modelo de usuário para salvar no banco de dados
            Usuarios usuario = new Usuarios();
            usuario.setEmail(email);
            usuario.setNome(nome);
            usuario.setSenha(senha);

            // Salva o usuário no banco de dados
            usuarioService.salvarUsuario(usuario);

            mv.setViewName("redirect:/auth/login");

        } catch (JwtException e) {

            System.out.println("Erro:" + e);
            mv.addObject("erro_jwt", e.getMessage());
            mv.setViewName("mails/confirm-email");

        } catch (Exception e){

            System.out.println("Erro: " + e);      
            mv.addObject("erro_excpetion", "Ocorreu um erro. Tente novamente mais tarde.");      
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
