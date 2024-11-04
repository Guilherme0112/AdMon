package com.br.AdMon.dao;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.AdMon.models.Usuarios;

public interface UsuarioDao extends JpaRepository<Usuarios, BigInteger>{

    @Query("SELECT u FROM Usuarios u WHERE u.email = :email")
    public Usuarios findByEmail(String email);

    @Query("SELECT l FROM Usuarios l WHERE l.email = :email AND l.senha = :senha")
    public Usuarios findLogin(String email, String senha);
}
