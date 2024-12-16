package com.br.AdMon.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.AdMon.Util.Util;
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
    public BigDecimal Dashboard_1(HttpSession http) throws Exception {

        // Inicializando as variaveis
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal totalContasPagas = BigDecimal.ZERO;
        BigDecimal totalGanhos = BigDecimal.ZERO;

        try {
            if (!Util.isAuth(http)) {

                throw new Exception("Você não tem autorização");
            }

            // Ano e mês atual
            LocalDate dataAtual = LocalDate.now();
            Integer anoAtual = dataAtual.getYear();
            Integer mesAtual = dataAtual.getMonthValue();

            Usuarios session = (Usuarios) http.getAttribute("session");

            List<Contas> contas_pagas = contarepositorio.findByEmailAndMonthAndYearAndStatus(session.getEmail(),
                                                                                            mesAtual,
                                                                                            anoAtual, "true");
            List<Ganhos> ganhos = ganhorepositorio.findByEmail(session.getEmail());
            List<Ganhos> ganhos_este_mes = ganhorepositorio.findByGanhosExpirationThisMonth(session.getEmail(),
                    anoAtual,
                    mesAtual);

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

        } catch (Exception e) {

            System.out.println("Erro: " + e.getMessage());
        }

        return total; // Retorna o valor total calculado
    }

    @GetMapping("/grafico_montanha_russa")
    public BigDecimal[] Dashboard_2(HttpSession http) throws Exception {

        // Vai retornar o total de cada mês em uma array
        // Cada valor representa um mês, vamos retornar os valores
        // dos últimos 4 mêses

        
        try {

            if (!Util.isAuth(http)) {
    
                throw new Exception("Você não tem autorização");
            }

            Usuarios session = (Usuarios) http.getAttribute("session");

            LocalDate dateNow = LocalDate.now();

            // Valores das datas dos últimos 4 meses
            int mes = dateNow.getMonthValue();
            int ano = dateNow.getYear();

            int mes1 = dateNow.minusMonths(1).getMonthValue();
            int ano1 = dateNow.minusMonths(1).getYear();

            int mes2 = dateNow.minusMonths(2).getMonthValue();
            int ano2 = dateNow.minusMonths(2).getYear();

            int mes3 = dateNow.minusMonths(3).getMonthValue();
            int ano3 = dateNow.minusMonths(3).getYear();

            // Query que busca todas as contas dos últimos 4 meses
            List<Contas> contasMes = contarepositorio.findByEmailAndMonthAndYearAndStatus(session.getEmail(), mes, ano,
                    "true");
            List<Contas> contasMes1 = contarepositorio.findByEmailAndMonthAndYearAndStatus(session.getEmail(), mes1,
                    ano1,
                    "true");
            List<Contas> contasMes2 = contarepositorio.findByEmailAndMonthAndYearAndStatus(session.getEmail(), mes2,
                    ano2,
                    "true");
            List<Contas> contasMes3 = contarepositorio.findByEmailAndMonthAndYearAndStatus(session.getEmail(), mes3,
                    ano3,
                    "true");

            // Query que busca todos os ganhos
            List<Ganhos> ganhos = ganhorepositorio.findByEmail(session.getEmail());

            // Busca os ganhos que são válidos somente para o mês
            List<Ganhos> ganhosMes = ganhorepositorio.findByGanhosExpirationThisMonth(session.getEmail(), ano, mes);
            List<Ganhos> ganhosMes1 = ganhorepositorio.findByGanhosExpirationThisMonth(session.getEmail(), ano1, mes1);
            List<Ganhos> ganhosMes2 = ganhorepositorio.findByGanhosExpirationThisMonth(session.getEmail(), ano2, mes2);
            List<Ganhos> ganhosMes3 = ganhorepositorio.findByGanhosExpirationThisMonth(session.getEmail(), ano3, mes3);

            // Retorna o saldo calculado
            BigDecimal saldo = Util.calcularSaldo(contasMes, ganhos, ganhosMes);
            BigDecimal saldo1 = Util.calcularSaldo(contasMes1, ganhos, ganhosMes1);
            BigDecimal saldo2 = Util.calcularSaldo(contasMes2, ganhos, ganhosMes2);
            BigDecimal saldo3 = Util.calcularSaldo(contasMes3, ganhos, ganhosMes3);

            BigDecimal[] dados = { saldo, saldo1, saldo2, saldo3 };

            return dados;

        } catch (Exception e) {

            System.out.println("Erro: " + e.getMessage());
            return new BigDecimal[] { BigDecimal.ZERO };
        }

        
    }

    @GetMapping("/dashboard_rosca_contas")
    public List<Contas> Dashboard_3(HttpSession http) throws Exception {

        try{
            if (!Util.isAuth(http)) {

                throw new Exception("Você não tem autorização");
            }

            // Ano e mês atual
            LocalDate dataAtual = LocalDate.now();
            Integer anoAtual = dataAtual.getYear();
            Integer mesAtual = dataAtual.getMonthValue();

            Usuarios session = (Usuarios) http.getAttribute("session");

            List<Contas> contas = contarepositorio.findByEmailAndMonthAndYear(session.getEmail(), mesAtual, anoAtual);

            return contas;

        } catch (Exception e){

            System.out.println("Erro: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @GetMapping("/dashboard_rosca_ganhos")
    public List<Ganhos> Dashboard_4(HttpSession http) throws Exception {
        try{
            if (!Util.isAuth(http)) {

                throw new Exception("Você não tem autorização");
            }

            Usuarios session = (Usuarios) http.getAttribute("session");

            List<Ganhos> ganhos = Stream.concat(
                    ganhorepositorio.findByEmail(session.getEmail()).stream(),
                    ganhorepositorio.findByMonthAndYearCurrent(session.getEmail()).stream())
                    .distinct()
                    .collect(Collectors.toList());

            return ganhos;
        } catch (Exception e) {
            
            System.out.println("Erro: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}