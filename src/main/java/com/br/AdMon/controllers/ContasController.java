package com.br.AdMon.controllers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.Util.Util;
import com.br.AdMon.dao.ContaDao;
import com.br.AdMon.models.Contas;
import com.br.AdMon.models.Usuarios;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ContasController {

    @Autowired
    private ContaDao contarepositorio;

    // Adicionar Contas
    @GetMapping("/contas/criar")
    public ModelAndView InserirConta(Contas conta, HttpSession http){

        ModelAndView mv = new ModelAndView();

        if(!Util.isAuth(http)){

            mv.setViewName("redirect:/auth/login");
            return mv;
        }

        mv.setViewName("contas/add-conta");
        mv.addObject("conta", new Contas());


        return mv;
    }

    @PostMapping("addConta")
    public ModelAndView InserirContaPost(@Valid Contas conta, BindingResult br, HttpSession http){
        
        ModelAndView mv = new ModelAndView();   
        if(!Util.isAuth(http)){

            mv.setViewName("redirect:/auth/login");
            return mv;
        }


        if(br.hasErrors()){

            // Retorna para exibir os erros
            mv.addObject("contas", conta);
            mv.setViewName("contas/add-conta");
        } else {

            // Recupera os dados da sessão
            Usuarios session = (Usuarios) http.getAttribute("session");

            // Salva os dados com o e-mail da sessão
            if(session != null){
                conta.setUserEmail(session.getEmail());
                contarepositorio.save(conta);
                mv.setViewName("redirect:/contas/lista");
                return mv;
            }

        }

        return mv;
    }

    // Listar Contas
    @GetMapping("/contas/lista")
    public ModelAndView ListarConta(Contas conta, HttpSession http){

        ModelAndView mv = new ModelAndView();

        if(!Util.isAuth(http)){
            mv.setViewName("redirect:/auth/login");
            return mv;
        } 

        // Valor total das contas
        BigDecimal totalConta = BigDecimal.ZERO;

        // Buscando dados no banco de dados
        Usuarios session = (Usuarios) http.getAttribute("session");
        List<Contas> contas = contarepositorio.findByEmail(session.getEmail());

        if(contas.size() > 0){
            for (Contas contaI : contas) {
                totalConta = totalConta.add(contaI.getValor());
            }
        }
        
        // Retorna os valores
        mv.addObject("contas", contas);
        mv.addObject("totalConta", totalConta);
        mv.setViewName("contas/list-conta");
            
        return mv;
    }

    @GetMapping("/contas/editar/{id}")
    public ModelAndView EditarConta(@PathVariable("id") BigInteger id, HttpSession http){
        ModelAndView mv = new ModelAndView();

        if(!Util.isAuth(http)){
            mv.setViewName("redirect:/auth/login");
            return mv;
        } 

        Contas conta = contarepositorio.findById(id).orElse(null);

        // Por algum motivo a data já vem formatada o que impede a inserção no input date, este trecho formata a data para um formato que o input aceite
        String vencimento = conta.getVencimento().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate vencimentoDate = LocalDate.parse(vencimento, formatter);

        if(conta != null){
            mv.setViewName("contas/editar-conta");
            mv.addObject("vencimento", vencimentoDate);
            mv.addObject("contas", conta);
        } else {

            // Se o id não existir no banco de dados, faz o redirecionamento
            mv.setViewName("redirect:/contas/lista");
        }


        return mv;
    }

    @PostMapping("editConta")
    public ModelAndView Editar(@Valid Contas conta, BindingResult br){

        ModelAndView mv = new ModelAndView();

        if(br.hasErrors()){

            mv.addObject("contas", conta);
            mv.setViewName("contas/editar-conta");
        } else { 

            contarepositorio.save(conta);
            mv.setViewName("redirect:/contas/lista");
        }

        return mv;
    }

    @GetMapping("/contas/excluir/{id}")
    public String Excluir(@PathVariable("id") BigInteger id, HttpSession http){

        if(!Util.isAuth(http)){
            return "redirect:/auth/login";
        }

        contarepositorio.deleteById(id);
        return "redirect:/contas/lista";
    }
}
