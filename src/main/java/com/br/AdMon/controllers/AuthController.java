package com.br.AdMon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.models.Usuarios;

@Controller
public class AuthController {
    
    @GetMapping("auth/login")
    public ModelAndView Login(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("auth/login");
        mv.addObject("usuarios", new Usuarios());

        return mv;
    }
}
