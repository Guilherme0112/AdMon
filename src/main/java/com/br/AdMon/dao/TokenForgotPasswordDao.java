package com.br.AdMon.dao;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.AdMon.models.TokensForgotPassword;

public interface TokenForgotPasswordDao extends JpaRepository<TokensForgotPassword, BigInteger>{

    
}
