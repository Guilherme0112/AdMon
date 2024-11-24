package com.br.AdMon.controllers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.Util.Util;
import com.br.AdMon.dao.ContaDao;
import com.br.AdMon.models.Contas;
import com.br.AdMon.models.Usuarios;
import com.br.AdMon.service.ServiceContas;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ContasController {

    @Autowired
    private ContaDao contarepositorio;

    @Autowired
    private ServiceContas serviceConta;

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

    @PostMapping("/addConta")
    public ModelAndView InserirContaPost(@Valid Contas conta, BindingResult br, HttpSession http, @RequestParam Integer meses){

        ModelAndView mv = new ModelAndView();   
        if(!Util.isAuth(http)){

            mv.setViewName("redirect:/auth/login");
            return mv;
        }

        // Recupera os dados da sessão
        Usuarios session = (Usuarios) http.getAttribute("session");

        if(br.hasErrors()){

            // Retorna para exibir os erros
            mv.addObject("contas", conta);
            mv.setViewName("contas/add-conta");
        } else {

            try{
                if (meses != null) {
                    if (meses > 0) {
                        for(int i = 0; i < meses; i++){

                            // Criando um registro para cada mês
                            Contas indexConta = new Contas();

                            indexConta.setConta(conta.getConta());
                            indexConta.setUserEmail(session.getEmail());
                            indexConta.setVencimento(conta.getVencimento().plusMonths(i + 1));
                            indexConta.setValor(conta.getValor());
                            indexConta.setAnotacao(conta.getAnotacao());

                            contarepositorio.save(indexConta);
                            
                        }
                    }
                }
            } catch (Exception e) {
                mv.addObject("errorMeses", "Erro ao calcular tempo");
                return mv;
            }

            // Salva os dados com o e-mail da sessão
            if(session != null){
                conta.setUserEmail(session.getEmail());
                contarepositorio.save(conta);
                mv.setViewName("redirect:/dashboard");
                return mv;
            }

        }

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
            mv.setViewName("redirect:/dashboard");
        }


        return mv;
    }

    @PostMapping("editConta")
    public ModelAndView Editar(@Valid Contas conta, BindingResult br, HttpSession http){

        ModelAndView mv = new ModelAndView();

        if(br.hasErrors()){

            mv.addObject("contas", conta);
            mv.setViewName("contas/editar-conta");
        } else { 
            Usuarios session = (Usuarios) http.getAttribute("session");
            conta.setUserEmail(session.getEmail());
            contarepositorio.save(conta);
            mv.setViewName("redirect:/dashboard");
        }

        return mv;
    }

    @PostMapping("/contas/pago/{id}")
    public String MarcarComoLido(@PathVariable("id") Integer id, HttpSession http){

        // Toda vez que o usuario clicar no botão para alterar como pago ou como não pago ele altera no banco de dados.
        Usuarios session = (Usuarios) http.getAttribute("session");
        serviceConta.alternarStatus(session.getEmail(), id);

        return "redirect:/dashboard";
    }

    @DeleteMapping("/contas/excluir/{id}")
    public String Excluir(@PathVariable("id") BigInteger id, HttpSession http){

        if(!Util.isAuth(http)){
            return "redirect:/auth/login";
        }

        contarepositorio.deleteById(id);
        return "redirect:/dashboard";
    }
}
