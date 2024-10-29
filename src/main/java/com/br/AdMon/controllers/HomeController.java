package com.br.AdMon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.dao.ContaDao;

@Controller
public class HomeController {

    @Autowired
    private ContaDao contaRepository;

    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home/index");
        return mv;
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("dashboard/dashboard");
        mv.addObject("contas", contaRepository.findContasLast7Days());
        return mv;
    }
}
