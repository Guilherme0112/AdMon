package com.br.AdMon.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.AdMon.dao.ContaDao;
import com.br.AdMon.dao.GanhoDao;
import com.br.AdMon.models.Contas;
import com.br.AdMon.models.Ganhos;
import com.br.AdMon.models.Usuarios;

import jakarta.servlet.http.HttpSession;


// O Controller responsável pelo GET do dashboard é o HomeController.java

@RestController
public class DashboardController {

    @Autowired
    private ContaDao contarepositorio;

    @Autowired
    private GanhoDao ganhorepositorio;

    @GetMapping("/dashboard_grafico_1")
    public BigDecimal Dashboard_1(HttpSession http) {

        BigDecimal total = BigDecimal.ZERO;
        // BigDecimal totalContas = BigDecimal.ZERO;
        BigDecimal totalContasPagas = BigDecimal.ZERO;
        BigDecimal totalGanhos = BigDecimal.ZERO;

        // Ano e mês atual
        LocalDate dataAtual = LocalDate.now();
        Integer anoAtual = dataAtual.getYear();
        Integer mesAtual = dataAtual.getMonthValue();

        Usuarios session = (Usuarios) http.getAttribute("session");

        // List<Contas> contas = contarepositorio.findByEmailAndMonthAndYearAndStatus(session.getEmail(), mesAtual, anoAtual, "false");
        List<Contas> contas_pagas = contarepositorio.findByEmailAndMonthAndYearAndStatus(session.getEmail(), mesAtual, anoAtual, "true");
        List<Ganhos> ganhos = ganhorepositorio.findByEmail(session.getEmail());
        List<Ganhos> ganhos_este_mes = ganhorepositorio.findByGanhosExpirationThisMonth(session.getEmail(), anoAtual, mesAtual);

        if (contas_pagas.size() > 0) {
            for (Contas contaI : contas_pagas) {
                totalContasPagas = totalContasPagas.add(contaI.getValor());
            }
        }

        // Retorna a soma de todos os ganhos
        if (ganhos.size() > 0) {
            for (Ganhos ganhosI : ganhos) {
                totalGanhos = totalGanhos.add(ganhosI.getValor());
            }
        }

        // Retorna a soma de todos os ganhos para somente este mês
        if (ganhos_este_mes.size() > 0) {
            for (Ganhos ganhosI : ganhos_este_mes) {
                totalGanhos = totalGanhos.add(ganhosI.getValor());
            }
        }

        total = totalGanhos.subtract(totalContasPagas);

        return total; // Retorna o valor total calculado
    }

    @GetMapping("/dashboard_rosca_contas")
    public List<Contas> Dashboard_2(HttpSession http) {

         // Ano e mês atual
         LocalDate dataAtual = LocalDate.now();
         Integer anoAtual = dataAtual.getYear();
         Integer mesAtual = dataAtual.getMonthValue();
    
         Usuarios session = (Usuarios) http.getAttribute("session");

         List<Contas> contas = contarepositorio.findByEmailAndMonthAndYear(session.getEmail(), mesAtual, anoAtual);

        return contas;
    }
    @GetMapping("/dashboard_rosca_ganhos")
    public List<Ganhos> Dashboard_3(HttpSession http) {
    
        Usuarios session = (Usuarios) http.getAttribute("session");

        List<Ganhos> ganhos = Stream.concat(
            ganhorepositorio.findByEmail(session.getEmail()).stream(), 
            ganhorepositorio.findByMonthAndYearCurrent(session.getEmail()).stream()
        )
        .distinct()
        .collect(Collectors.toList());

        return ganhos;
    }
}