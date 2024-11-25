package com.br.AdMon.Util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.servlet.http.HttpSession;

public class Util {

    // Criptografia de senhas
    public static String md5(String senha) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
        return String.format("%032x", hash);
    }
    

    // Verifica se o usuário está logado
    public static Boolean isAuth(HttpSession http){
        if(http.getAttribute("session") == null){
            return false;
        }

        return true;

    }


}
