package com.br.AdMon.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.AdMon.models.Ganhos;

public interface GanhoDao extends JpaRepository<Ganhos, BigInteger>{
    @Query(value = "SELECT * FROM ganhos WHERE criado >= DATE_SUB(CURDATE(), INTERVAL 28 DAY)", nativeQuery = true)
    List<Ganhos> findGanhosLastMonth();
}
