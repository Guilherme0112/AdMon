package com.br.AdMon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.Util.Util;
import com.br.AdMon.models.Usuarios;
import com.br.AdMon.service.ServiceUsuario;

import jakarta.servlet.http.HttpSession;

@Controller
public class PerfilController {

    @Autowired
    private ServiceUsuario usuarioService;

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
    public ModelAndView PerfilSenhaPost(@RequestBody String senhaAntiga, @RequestBody String senhaNova, HttpSession http) throws  Exception{
        ModelAndView mv = new ModelAndView();

        if(!Util.isAuth(http)){

            mv.setViewName("redirect:/auth/login");
            return mv;
        }

        Usuarios session = (Usuarios) http.getAttribute("session");
        Usuarios usuario = usuarioService.loginUsuario(session.getEmail(), Util.md5(senhaAntiga));

        if(usuario != null){
            usuarioService.alterarSenha(session.getEmail(), senhaAntiga, senhaNova);
        }

        mv.setViewName("perfil/perfil");

        return mv;
    }
        
}
