package com.br.AdMon.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ServiceScheduling {


    @Scheduled(cron = "59 59 23 L * ?")
    public void RegistroMensalDosDados(){

        // Lógica para calcular se o usuário fechou o mês no positivo ou negativo
        // Se negativo criar uma conta com o valor e com o nome "Dívidas do mês antorior"
        // Se positivo criarum ganho somente para este mês com o nome "Saldo do mês anterior"
    }
}
