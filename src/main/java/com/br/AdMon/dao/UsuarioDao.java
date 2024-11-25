package com.br.AdMon.dao;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.AdMon.models.Usuarios;

import jakarta.transaction.Transactional;

public interface UsuarioDao extends JpaRepository<Usuarios, BigInteger>{

    @Query("SELECT u FROM Usuarios u WHERE u.email = :email")
    public Usuarios findByEmail(String email);

    @Query("SELECT l FROM Usuarios l WHERE l.email = :email AND l.senha = :senha")
    public Usuarios findLogin(String email, String senha);

    @Modifying
    @Transactional
    @Query("UPDATE Usuarios u SET u.senha = :senha WHERE u.email = :email")
    void updatePassword(@Param("email") String email, @Param("senha") String senha);
}
