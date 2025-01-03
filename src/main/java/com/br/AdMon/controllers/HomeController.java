package com.br.AdMon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.Util.Util;
import com.br.AdMon.dao.ContaDao;
import com.br.AdMon.models.Usuarios;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private ContaDao contaRepository;

    @GetMapping("/")
    public ModelAndView Index(HttpSession http){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("home/index");
        return mv;
    }

    @GetMapping("/dashboard")
    public ModelAndView Dashboard(HttpSession http){
        
        ModelAndView mv = new ModelAndView();

        if(!Util.isAuth(http)){

            mv.setViewName("redirect:/auth/login");
            return mv;
        }
        
        Usuarios session = (Usuarios) http.getAttribute("session");

        mv.setViewName("dashboard/dashboard");
        mv.addObject("contas", contaRepository.findContasLast7Days(session.getEmail()));

        return mv;
    }

    @GetMapping("/termos")
    public ModelAndView TermosDeUso(){

        ModelAndView mv = new ModelAndView();

        mv.setViewName("others/termos_de_uso");
        return mv;
    }

}
