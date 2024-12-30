package com.br.AdMon.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.AdMon.models.Contas;

import jakarta.transaction.Transactional;

@Repository
public interface ContaDao extends JpaRepository<Contas, BigInteger>{

    @Query("SELECT c FROM Contas c WHERE c.id = :id")
    Contas findContaById(@Param("id") BigInteger id);

    @Query(value = "SELECT * FROM contas WHERE criado >= DATE_SUB(CURDATE(), INTERVAL 28 DAY)", nativeQuery = true)
    List<Contas> findContasLastMonth();


    @Query(value = "SELECT * FROM Contas c WHERE c.pago = 'false' AND c.user_email = :email AND c.vencimento BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)", nativeQuery = true)
    List<Contas> findContasLast7Days(String email);

    @Query("SELECT e FROM Contas e WHERE e.userEmail = :email AND MONTH(e.vencimento) = :mes AND YEAR(e.vencimento) = :ano AND e.pago = :status")
    public List<Contas> findByEmailAndMonthAndYearAndStatus(String email,
                                                            Integer mes,
                                                            Integer ano,
                                                            String status);
    
    @Query("SELECT e FROM Contas e WHERE e.userEmail = :email AND MONTH(e.vencimento) = :mes AND YEAR(e.vencimento) = :ano")
    public List<Contas> findByEmailAndMonthAndYear(String email,
                                                   Integer mes,
                                                   Integer ano);

    @Query("SELECT e FROM Contas e WHERE e.userEmail = :email AND e.pago = :status")
    public List<Contas> findByEmailAndStatus(String email,
                                             String status);

    @Query("SELECT c FROM Contas c WHERE MONTH(c.vencimento) = :mes AND YEAR(c.vencimento) = :ano")
    List<Contas> findByMonthAndYear(Integer mes,
                                    Integer ano);

    @Query("SELECT c FROM Contas c WHERE c.userEmail = :email AND c.Id = :id")
    public List<Contas> findByEmailAndId(@Param("email") String email,
                                         @Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Contas c SET c.pago = :status WHERE c.userEmail = :email AND c.Id = :id")
    void updateStatusByEmail(@Param("status") String status,
                             @Param("email") String email,
                             @Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Contas c WHERE c.userEmail = :email")
    void deleteByEmail(@Param("email") String email);
}
