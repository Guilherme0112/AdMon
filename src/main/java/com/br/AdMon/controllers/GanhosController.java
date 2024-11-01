package com.br.AdMon.controllers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.dao.GanhoDao;
import com.br.AdMon.models.Ganhos;

import jakarta.validation.Valid;

@Controller 
public class GanhosController {

    @Autowired
    private GanhoDao ganhorepositorio;

    @GetMapping("/ganhos/lista")
    public ModelAndView ListarGanhos(Ganhos ganho){

        BigDecimal totalGanho = BigDecimal.ZERO;

        ModelAndView mv = new ModelAndView();

        List<Ganhos> ganhos = ganhorepositorio.findAll();


        if(ganhos.size() > 0){
            for(Ganhos ganhosI : ganhos){
                totalGanho = totalGanho.add(ganhosI.getValor());
            }
        }

        mv.addObject("totalGanho", totalGanho);
        mv.addObject("ganhos", ganhos);
        mv.setViewName("ganhos/list-ganho");

        return mv;
    }

    @GetMapping("/ganhos/criar")
    public ModelAndView InserirGanhos(Ganhos ganho){
        ModelAndView mv = new ModelAndView();


        mv.addObject("ganho", new Ganhos());
        mv.setViewName("ganhos/add-ganho");
        return mv;
    }
    @PostMapping("/ganhos/criar")
    public ModelAndView InserirGanhosPost(@Valid Ganhos ganho, BindingResult br){
        ModelAndView mv = new ModelAndView();

        if(br.hasErrors()){
            mv.addObject("ganho", ganho);
            mv.setViewName("ganhos/add-ganho");
        } else {
            
            ganhorepositorio.save(ganho);
            mv.setViewName("redirect:/ganhos/lista");            
        }

        return mv;
    }

    @GetMapping("/ganhos/editar/{id}")
    public ModelAndView EditarGanho(@PathVariable("id") BigInteger id){
        ModelAndView mv = new ModelAndView();

        Ganhos ganho = ganhorepositorio.findById(id).orElse(null);

        if(ganho != null){
           
            mv.setViewName("ganhos/editar-ganho");
            mv.addObject("ganhos", ganho);
        } else {

            mv.setViewName("redirect:/ganhos/lista");
        }


        return mv;
    }

    @PostMapping("editGanho")
    public ModelAndView EditarGanhoPost(@Valid Ganhos ganho, BindingResult br){
        ModelAndView mv = new ModelAndView();
        
        if(br.hasErrors()){

            mv.addObject("ganho", ganho);
            mv.setViewName("ganhos/editar-ganho");
        } else {

            ganhorepositorio.save(ganho);
            mv.setViewName("redirect:/ganhos/lista");
        }
        return mv;
    }

    @GetMapping("ganhos/excluir/{id}")
    public String ExcluirGanho(@PathVariable("id") BigInteger id){

        ganhorepositorio.deleteById(id);
        return "redirect:/ganhos/lista";
    }
}
