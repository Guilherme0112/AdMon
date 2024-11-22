package com.br.AdMon.controllers;

import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.Util.Util;
import com.br.AdMon.dao.ContaDao;
import com.br.AdMon.dao.GanhoDao;
import com.br.AdMon.models.Usuarios;

import jakarta.servlet.http.HttpSession;

@Controller
public class CalendarioController {

    @Autowired
    private ContaDao contaRepository;

    @Autowired
    private GanhoDao ganhoRepository;

    @GetMapping("/calendario/{ano}")
    public ModelAndView Calendario(HttpSession http, @PathVariable("ano") Integer ano){
        ModelAndView mv = new ModelAndView();

        if(!Util.isAuth(http)){
            mv.setViewName("redirect:/auth/login");
            return mv;
        }

        Integer anoAtual = Year.now().getValue();

        // Se o usuário colocar na url algum ano maior que o ano atual + 30, ele retornará a página com o ano atual
        if(ano > anoAtual + 30 || ano < anoAtual){

            mv.setViewName("redirect:/calendario/" + anoAtual);
            return mv;
        }

        mv.addObject("ano", ano);
        mv.setViewName("calendario/meses");

        return mv;
    }

    @GetMapping("/calendario/{mes}/{ano}")
    public ModelAndView Mes(HttpSession http, @PathVariable("mes") Integer mes, @PathVariable("ano") Integer ano){
        ModelAndView mv = new ModelAndView();

        if(!Util.isAuth(http)){
            mv.setViewName("redirect:/auth/login");
            return mv;
        }

        Usuarios session = (Usuarios) http.getAttribute("session");
        String mesString = null;

        // Retorna o nome do mês
        switch (mes) {
            case 1 -> mesString = "Janeiro";
            case 2 -> mesString = "Fevereiro";
            case 3 -> mesString = "Março";
            case 4 -> mesString = "Abril";
            case 5 -> mesString = "Maio";
            case 6 -> mesString = "Junho";
            case 7 -> mesString = "Julho";
            case 8 -> mesString = "Agosto";
            case 9 -> mesString = "Setembro";
            case 10 -> mesString = "Outubro";
            case 11 -> mesString = "Novembro";
            case 12 -> mesString = "Dezembro";
            default -> throw new IllegalArgumentException("Mês inválido");
        }

        mv.addObject("mes", mesString);
        mv.addObject("contas", contaRepository.findByEmailAndMonthAndYearAndStatus(session.getEmail(), mes, ano, "false", false));
        mv.addObject("contas_pagas", contaRepository.findByEmailAndMonthAndYearAndStatus(session.getEmail(), mes, ano, "true", false));
        mv.addObject("ganhos", ganhoRepository.findByEmail(session.getEmail()));
        mv.addObject("ganhos_este_mes", ganhoRepository.findByGanhosExpirationThisMonth(session.getEmail(), ano, mes));
        mv.setViewName("calendario/mes");

        return mv;
    }

    // Tratamento de erro específico para tipo incorreto no @PathVariable
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView handleTypeMismatchException() {

        Integer anoAtual = Year.now().getValue();
        return new ModelAndView("redirect:/calendario/" + anoAtual);
    }
}
