package com.br.AdMon.Util;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.br.AdMon.models.Contas;
import com.br.AdMon.models.Ganhos;

import jakarta.servlet.http.HttpSession;

public class Util {
    
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Criptografia de senhas
    public static String criptografar(String senha){
        return encoder.encode(senha);
    }

    public static boolean verificaSenha(String senha, String hash){
        return encoder.matches(senha, hash);
    }

    // Verifica se o usuário está logado
    public static Boolean isAuth(HttpSession http){

        if(http.getAttribute("session") == null){
            
            return false;
        }

        return true;

    }

    public static BigDecimal calcularSaldo(List<Contas> contas, List<Ganhos> ganhos, List<Ganhos> somenteEsteMes){

        BigDecimal totalGanhos = BigDecimal.ZERO;
        BigDecimal totalContas = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        for(Contas contaI : contas){
            totalContas = totalContas.add(contaI.getValor());
        }

        for(Ganhos ganhoI : ganhos){
            totalGanhos = totalGanhos.add(ganhoI.getValor());
        }

        for(Ganhos somenteEsteMesI : somenteEsteMes){
            totalGanhos = totalGanhos.add(somenteEsteMesI.getValor());
        }


        total = totalGanhos.subtract(totalContas);

        if(total.compareTo(totalGanhos) == 0){
            total = BigDecimal.ZERO;
        }

        return total;
    }

    public static String generateToken(){
        return UUID.randomUUID().toString();
    }

}
