package com.br.AdMon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.dao.UsuarioDao;
import com.br.AdMon.models.Usuarios;

import jakarta.validation.Valid;

@Controller
public class AuthController {
    
    @Autowired
    private UsuarioDao usuarioRepository;

    @GetMapping("auth/login")
    public ModelAndView Login(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("auth/login");
        mv.addObject("usuarios", new Usuarios());

        return mv;
    }
    @PostMapping("auth/login")
    public ModelAndView LoginPOST(@Valid Usuarios usuarios, BindingResult br){
        ModelAndView mv = new ModelAndView();

        if(br.hasErrors()){

            mv.addObject("usuarios", new Usuarios());
            mv.setViewName("auth/login");
        } else {

            // Inicia a sessão
            
            mv.setViewName("redirect:/dashboard");
        }

        return mv;
    }

    @GetMapping("auth/register")
    public ModelAndView Registrar(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("usuarios", new Usuarios());

        return mv;
    }

    public ModelAndView RegistroPOST(@Valid Usuarios usuarios, BindingResult br){
        ModelAndView mv = new ModelAndView();

        if(br.hasErrors()){

            mv.addObject("usuarios", new Usuarios());
            mv.setViewName("auth/register");
        } else {

            usuarioRepository.save(usuarios);
            mv.setViewName("redirect:/dashboard");
        }

        return mv;
    }

}
