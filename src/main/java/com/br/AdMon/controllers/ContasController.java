package com.br.AdMon.controllers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        // Inserindo dados no banco de dados
        conta.setStatus("Pendente");
        contarepositorio.save(conta);
        mv.setViewName("redirect:/");
        return mv;
    }

    // Listar Contas
    @GetMapping("/listaConta")
    public ModelAndView ListarConta(Contas conta){

        // Valor total das contas
        BigDecimal total = BigDecimal.ZERO;
        String totalString = "R$ 0,00";

        ModelAndView mv = new ModelAndView();

        // Buscando dados no banco de dados
        List<Contas> contas = contarepositorio.findAll();

        // Formata o valor da conta para BRL
        Locale locale = Locale.of("pt", "BR");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);

        if(contas.size() > 0){
            for (Contas contaI : contas) {

                // Formata o valor das contas
                String valorString = formatter.format(contaI.getValor());
                contaI.setValorF(valorString);

                // Calcula o total da conta
                total = total.add(contaI.getValor());

                // Formata o total da conta
                totalString = formatter.format(total);
            }
        }

        // Retorna os valores
        mv.addObject("contas", contas);
        mv.addObject("total", totalString);
        mv.setViewName("contas/list-conta");
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView EditarConta(@PathVariable("id") BigInteger id){
        ModelAndView mv = new ModelAndView();

        Contas conta = contarepositorio.findById(id).orElse(null);

        if(conta == null){
            mv.setViewName("redirect:/listaConta");
        } else {
            mv.setViewName("contas/editar-conta");
            mv.addObject("conta", conta);
        }
        return mv;
    }

    @PostMapping("editConta")
    public ModelAndView Editar(Contas conta){

        ModelAndView mv = new ModelAndView();
        conta.setStatus("Pendente");
        contarepositorio.save(conta);
        mv.setViewName("redirect:/listaConta");
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public String Excluir(@PathVariable("id") BigInteger id){
        contarepositorio.deleteById(id);
        return "redirect:/listaConta";
    }
}
