package com.br.AdMon.models;

import java.math.BigInteger;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tokens")
public class Tokens {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    private String token;

    private LocalDate expire_in;

    public BigInteger getId(){
        return id;
    }
    public String getToken(){
        return token;
    }
    public LocalDate getExpireIn(){
        return expire_in;
    }
}
