package com.br.AdMon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.dao.ContaDao;
import com.br.AdMon.models.Contas;

@Controller
public class ContasController {

    @Autowired
    private ContaDao contarepositorio;

    @GetMapping("/addConta")
    public ModelAndView InserirConta(Contas conta){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("contas/add-conta");
        mv.addObject("conta", new Contas());
        return mv;
    }

    @PostMapping("addConta")
    public ModelAndView InserirContaPost(Contas conta){
        ModelAndView mv = new ModelAndView();
        contarepositorio.save(conta);
        mv.setViewName("redirect:/");
        return mv;
    }

}
