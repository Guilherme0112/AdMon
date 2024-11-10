package com.br.AdMon.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.br.AdMon.models.Contas;

@Repository
public interface ContaDao extends JpaRepository<Contas, BigInteger>{

    @Query(value = "SELECT * FROM contas WHERE criado >= DATE_SUB(CURDATE(), INTERVAL 28 DAY)", nativeQuery = true)
    List<Contas> findContasLastMonth();


    @Query(value = "SELECT * FROM contas WHERE vencimento BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)", nativeQuery = true)
    List<Contas> findContasLast7Days();

    @Query("SELECT e FROM Contas e WHERE e.userEmail = :email")
    public List<Contas> findByEmail(String email);

}
