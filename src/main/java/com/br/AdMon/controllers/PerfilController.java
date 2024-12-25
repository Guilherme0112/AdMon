package com.br.AdMon.controllers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.Util.Util;
import com.br.AdMon.models.Usuarios;
import com.br.AdMon.service.ServiceAuth;
import com.br.AdMon.service.ServiceUsuario;

import jakarta.servlet.http.HttpSession;

@Controller
public class PerfilController {

    @Autowired
    private ServiceUsuario usuarioService;

    @Autowired
    private ServiceAuth authService;

    @GetMapping("/perfil/editar")
    public ModelAndView PerfilSenha(HttpSession http){

        ModelAndView mv = new ModelAndView();

        if(!Util.isAuth(http)){

            mv.setViewName("redirect:/auth/login");
            return mv;
        }

        mv.setViewName("perfil/perfil");
        return mv;
    }

    @PostMapping("/perfil/editar")
    public ModelAndView PerfilSenhaPost(@RequestParam String senhaAntiga, @RequestParam String senhaNova, HttpSession http) throws  Exception{

        ModelAndView mv = new ModelAndView();

        if(!Util.isAuth(http)){

            mv.setViewName("redirect:/auth/login");
            return mv;
        }

        try{

            Usuarios session = (Usuarios) http.getAttribute("session");
            Usuarios usuario = usuarioService.loginUsuario(session.getEmail(), senhaAntiga);

            if(usuario == null){

                mv.addObject("msgError", "Erro ao atualizar senha. Tente novamente mais tarde");
                mv.setViewName("perfil/perfil");
            }

            if(!Util.verificaSenha(senhaNova, senhaAntiga)){
                throw new Exception("A senha está incorreta");
            }
            
            usuarioService.alterarSenha(session.getEmail(), senhaNova);
            mv.setViewName("redirect:/dashboard");
            
        } catch (Exception err){

            mv.addObject("msgError", err.getMessage());
            mv.setViewName("perfil/perfil");
        }

        return mv;
    }

    @PostMapping("/perfil/deletar")
    public ResponseEntity<Map<String, String>> deletarConta(@RequestBody Map<String, Boolean> body, HttpSession http) throws Exception{

        if(!Util.isAuth(http)){

            return ResponseEntity.ok(Map.of("redirect", "/auth/login"));
        }

        Boolean del = body.get("del");

        if(Boolean.TRUE.equals(del)){

            try {

                Usuarios session = (Usuarios) http.getAttribute("session");
                usuarioService.DeletarUsuario(session.getEmail());
                authService.Logout(http);

            } catch (Exception e) {
                
                throw new Exception("Erro: ", e);
            }
                
        }

        return ResponseEntity.ok(Map.of("redirect", "/auth/login"));
    }

}
