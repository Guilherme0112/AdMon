package com.br.AdMon.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;
// import java.util.stream.Stream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.br.AdMon.dao.ContaDao;
import com.br.AdMon.dao.GanhoDao;
import com.br.AdMon.dao.UsuarioDao;
import com.br.AdMon.models.Contas;
import com.br.AdMon.models.Ganhos;
import com.br.AdMon.models.Usuarios;

@Service
public class ServiceScheduling {

    @Autowired
    private UsuarioDao usuarioRepository;

    @Autowired
    private GanhoDao ganhoRepository;

    @Autowired
    private ContaDao contaRepository;

    @Scheduled(cron = "0 0 0 1 * *")
    public void RegistroMensalDosDados(){

        // Lógica para calcular se o usuário fechou o mês no positivo ou negativo
        // Se negativo criar uma conta com o valor e com o nome "Dívidas do mês antorior"
        // Se positivo criarum ganho somente para este mês com o nome "Saldo do mês anterior"

        // DataAtual
        LocalDate data = LocalDate.now();
        int mes = data.getMonthValue();
        int ano = data.getYear();
        LocalDate ultimoDiaDoMes = data.with(TemporalAdjusters.lastDayOfMonth());

        // Inicializando variaveis
        BigDecimal totalGanhos = BigDecimal.ZERO;
        BigDecimal totalContas = BigDecimal.ZERO;
        BigDecimal totalSaldo = BigDecimal.ZERO;

        // Busca os e-mails
        List<Usuarios> usuarios = usuarioRepository.findAll();

        // Inicia buscando dados do usuário
        for(Usuarios usuario : usuarios){
            List<Ganhos> ganhos = Stream.concat(
                ganhoRepository.findByEmail(usuario.getEmail()).stream(),
                ganhoRepository.findByMonthAndYearCurrent(usuario.getEmail()).stream()
            )
            .distinct()
            .collect(Collectors.toList());
            
            // Soma os ganhos válidos do usuário
            for(Ganhos ganho : ganhos){

                totalGanhos = totalGanhos.add(ganho.getValor());
            }
            

            // Soma das contas
            List<Contas> contas = contaRepository.findByEmailAndMonthAndYear(usuario.getEmail(), mes, ano);

            //Somando
            for(Contas conta : contas){
                totalContas = totalContas.add(conta.getValor());
            }

            totalSaldo = totalGanhos.subtract(totalContas);
            
            if(totalSaldo.signum() > 0){
                Ganhos addGanho = new Ganhos();

                addGanho.setAnotacao("Saldo do mês anterior");
                addGanho.setEsteMes(true);
                addGanho.setUserEmail(usuario.getEmail());
                addGanho.setValor(totalSaldo);
                addGanho.setGanho("Saldo do mês anterior");

                ganhoRepository.save(addGanho);
                System.out.println("Ganho salvo");

            } else if (totalSaldo.signum() < 0){

                Contas addConta = new Contas();

                addConta.setAnotacao("Saldo do mês anterior");
                addConta.setPago("false");
                addConta.setUserEmail(usuario.getEmail());
                addConta.setValor(totalSaldo.abs());
                addConta.setConta("Saldo do mês anterior");
                addConta.setVencimento(ultimoDiaDoMes);

                contaRepository.save(addConta);

                System.out.println("Conta salva");
            }

    
            totalContas = BigDecimal.ZERO;
            totalGanhos = BigDecimal.ZERO;
            totalSaldo = BigDecimal.ZERO;
        }

    }
}
