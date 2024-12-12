package com.br.AdMon.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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



}
