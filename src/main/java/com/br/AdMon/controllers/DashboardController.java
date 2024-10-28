package com.br.AdMon.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.AdMon.dao.ContaDao;
import com.br.AdMon.dao.GanhoDao;
import com.br.AdMon.models.Contas;
import com.br.AdMon.models.Ganhos;

@RestController
public class DashboardController {

    @Autowired
    private ContaDao contarepositorio;
    @Autowired
    private GanhoDao ganhorepositorio;

    @GetMapping("/dashboard-dado-1")
    public BigDecimal Dashboard_1() {

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal totalContas = BigDecimal.ZERO;
        BigDecimal totalGanhos = BigDecimal.ZERO;

        // Busca os dados
        List<Contas> contas = contarepositorio.findContasLastMonth();
        List<Ganhos> ganhos = ganhorepositorio.findGanhosLastMonth();

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

    @GetMapping("/dashboard-dado-2")
    public List<Contas> Dashboard_2() {
    
        List<Contas> contas = contarepositorio.findAll();

        return contas;
    }
}