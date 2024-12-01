package com.br.AdMon.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.AdMon.models.Ganhos;

import jakarta.transaction.Transactional;

public interface GanhoDao extends JpaRepository<Ganhos, BigInteger>{
    @Query(value = "SELECT * FROM ganhos WHERE criado >= DATE_SUB(CURDATE(), INTERVAL 28 DAY)", nativeQuery = true)
    List<Ganhos> findGanhosLastMonth();

    @Query("SELECT e FROM Ganhos e WHERE e.userEmail = :email AND e.esteMes = false")
    List<Ganhos> findByEmail(@Param("email") String email);

    @Query("SELECT g FROM Ganhos g WHERE MONTH(g.criado) = MONTH(CURDATE) AND YEAR(g.criado) = YEAR(CURDATE) AND g.esteMes = true AND g.userEmail = :email")
    List<Ganhos> findByMonthAndYearCurrent(@Param("email") String email);

    @Query("SELECT g FROM Ganhos g WHERE g.id = :id AND g.userEmail = :email")
    public Ganhos findByEmailAndId(@Param("email") String email, @Param("id") Integer id);

    @Query("SELECT g FROM Ganhos g WHERE g.userEmail = :email AND g.esteMes = true AND YEAR(g.criado) = :ano AND MONTH(g.criado) = :mes")
    List<Ganhos> findByGanhosExpirationThisMonth(@Param("email") String email, @Param("ano") Integer ano, @Param("mes") Integer mes);

    @Modifying
    @Transactional
    @Query("DELETE FROM Ganhos g WHERE g.userEmail = :email")
    void deleteByEmail(@Param("email") String email);
}
