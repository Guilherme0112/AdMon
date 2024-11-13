package com.br.AdMon.controllers;

import java.math.BigDecimal;
import java.util.List;


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
        BigDecimal totalContas = BigDecimal.ZERO;
        BigDecimal totalGanhos = BigDecimal.ZERO;

        // Busca os dados
        Usuarios session = (Usuarios) http.getAttribute("session");

        List<Contas> contas = contarepositorio.findByEmailAndMonthAndYear(session.getEmail(), 12, 2024);
        List<Ganhos> ganhos = ganhorepositorio.findByEmail(session.getEmail());

        // Retorna a soma de todas as contas
        if (contas.size() > 0) {
            for (Contas contaI : contas) {
                totalContas = totalContas.add(contaI.getValor());
            }
        }

        // Retorna a soma de todos os ganhos
        if (ganhos.size() > 0) {
            for (Ganhos ganhosI : ganhos) {
                totalGanhos = totalGanhos.add(ganhosI.getValor());
            }
        }

        total = totalGanhos.subtract(totalContas);

        return total; // Retorna o valor total calculado
    }

    @GetMapping("/dashboard_rosca_contas")
    public List<Contas> Dashboard_2() {
    
        List<Contas> contas = contarepositorio.findAll();

        return contas;
    }
    @GetMapping("/dashboard_rosca_ganhos")
    public List<Ganhos> Dashboard_3() {
    
        List<Ganhos> ganhos = ganhorepositorio.findAll();

        return ganhos;
    }
}