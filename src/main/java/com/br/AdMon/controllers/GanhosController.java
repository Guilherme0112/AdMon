package com.br.AdMon.controllers;


import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.Util.Util;
import com.br.AdMon.dao.GanhoDao;
import com.br.AdMon.exceptions.SaveGanhoException;
import com.br.AdMon.models.Ganhos;
import com.br.AdMon.models.Usuarios;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller 
public class GanhosController {

    @Autowired
    private GanhoDao ganhoRepository;

    @GetMapping("/ganhos/criar")
    public ModelAndView InserirGanhos(Ganhos ganho, HttpSession http){

        ModelAndView mv = new ModelAndView();

        if(!Util.isAuth(http)){
            mv.setViewName("redirect:/auth/login");
            return mv;
        }

        mv.addObject("ganho", new Ganhos());
        mv.setViewName("ganhos/add-ganho");
        return mv;
    }
    @PostMapping("/ganhos/criar")
    public ModelAndView InserirGanhosPost(@Valid Ganhos ganho, BindingResult br, HttpSession http){

        ModelAndView mv = new ModelAndView();

        if(br.hasErrors()){

            mv.addObject("ganho", ganho);
            mv.setViewName("ganhos/add-ganho");
        } else {
            
            if(Boolean.TRUE.equals(ganho.getEsteMes())){
                ganho.setEsteMes(true);
            }

            Usuarios session = (Usuarios) http.getAttribute("session");

            ganho.setUserEmail(session.getEmail());
            ganhoRepository.save(ganho);
            mv.setViewName("redirect:/dashboard");            
        }

        return mv;
    }

    @GetMapping("/ganhos/editar/{id}")
    public ModelAndView EditarGanho(@PathVariable("id") Integer id, HttpSession http){

        ModelAndView mv = new ModelAndView();

        if(!Util.isAuth(http)){
            mv.setViewName("redirect:/auth/login");
            return mv;
        }

        Usuarios session = (Usuarios) http.getAttribute("session");
        Ganhos ganhos = ganhoRepository.findByEmailAndId(session.getEmail(), id);

        if (ganhos != null) {

            mv.setViewName("ganhos/editar-ganho");
            mv.addObject("ganhos", ganhos);
        } else {
            mv.setViewName("redirect:/dashboard");
        }
        


        return mv;
    }

    @PostMapping("/ganhos/editar")
    public ModelAndView EditarGanhoPost(@Valid Ganhos ganho, BindingResult br, HttpSession http) throws SaveGanhoException{
        
        ModelAndView mv = new ModelAndView();
        
        if(!Util.isAuth(http)){
            mv.setViewName("redirect:/auth/login");
            return mv;
        }

        if(br.hasErrors()){

            mv.addObject("ganho", ganho);
            mv.setViewName("ganhos/editar-ganho");
            return mv;
        } 

        try {

            Usuarios session = (Usuarios) http.getAttribute("session");
            
            Ganhos ganhoEdit = ganhoRepository.findGanhoById(ganho.getId());

            if(ganhoEdit == null){
                throw new SaveGanhoException("Ocorreu aalgum erro. Tente novamente mais tarde");
            }

            if(!ganhoEdit.getUserEmail().equals(session.getEmail())){
                throw new SaveGanhoException("Não foi possível editar este registro. Tente novamente mais tarde");
            }

            ganho.setUserEmail(session.getEmail());
            ganhoRepository.save(ganho);

            mv.setViewName("redirect:/dashboard");

        } catch (SaveGanhoException e) {
            
            mv.addObject("erro_vali", e.getMessage());
            mv.addObject("ganho", ganho);
            mv.setViewName("ganhos/editar-ganho");
        }

        return mv;
    }

    @DeleteMapping("ganhos/excluir/{id}")
    public String ExcluirGanho(@PathVariable("id") BigInteger id, HttpSession http){

        if(!Util.isAuth(http)){
            return "redirect:/auth/login";
        }

        ganhoRepository.deleteById(id);
        return "redirect:/dashboard";
    }
}
