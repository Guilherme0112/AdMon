package com.br.AdMon.controllers;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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

    // Adicionar Contas
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

    // Listar Contas
    @GetMapping("/listaConta")
    public ModelAndView ListarConta(Contas conta){

        ModelAndView mv = new ModelAndView();

        // Buscando dados no banco de dados
        List<Contas> contas = contarepositorio.findAll();

        Locale locale = Locale.of("pt", "BR");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        for (Contas contaI : contas) {
            String valorString = formatter.format(contaI.getValor());
            contaI.setValorF(valorString);
        }


        mv.addObject("contas", contas);
        mv.setViewName("contas/list-conta");
        return mv;
    }
}
