package com.br.AdMon.dao;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.AdMon.models.Contas;

public interface ContaDao extends JpaRepository<Contas, BigInteger>{

}
