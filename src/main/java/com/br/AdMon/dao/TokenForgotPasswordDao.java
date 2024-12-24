package com.br.AdMon.dao;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.AdMon.models.TokensForgotPassword;

public interface TokenForgotPasswordDao extends JpaRepository<TokensForgotPassword, BigInteger>{

    @Query("SELECT t FROM TokensForgotPassword t WHERE t.token = :token")
    public TokensForgotPassword findByToken(@Param("token") String token);
    
}
