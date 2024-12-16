package com.br.AdMon.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.AdMon.models.Tokens;

import jakarta.transaction.Transactional;

public interface TokensDao extends JpaRepository<Tokens, BigInteger>{

    @Query("SELECT t FROM Tokens t WHERE t.token = :token")
    List<Tokens> findByToken(@Param("token") String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM Tokens t WHERE t.token = :token AND t.userEmail = :email")
    void deleteByToken(@Param("token") String token, @Param("email") String email);
}
