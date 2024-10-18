package com.br.AdMon.models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import com.br.AdMon.Enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="contas")
public class Contas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;
    
    @Column(name="conta")
    private String conta;
    
    @Column(name="valor")
    private BigDecimal valor;
    
    @Column(name="anotacao")
    private String anotacao;
    
    @Column(name="vencimento")
    private LocalDate vencimento;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;

    // Getters

    public BigInteger getId(){
        return id;
    }
    public String getConta(){
        return conta;
    }
    public BigDecimal getValor(){
        return valor;
    }

    public String getAnotacao(){
        return anotacao;
    }
    public LocalDate getVencimento(){
        return vencimento;
    }
    public Status getStatus(){
        return status;
    }

    // Setters

    public void setId(BigInteger id){
        this.id = id;
    }
    public void setConta(String conta){
        this.conta = conta;
    }
    public void setValor(BigDecimal valor){
        this.valor = valor;
    }
    public void setAnotacao(String anotacao){
        this.anotacao = anotacao;
    }
    public void setVencimento(LocalDate vencimento){
        this.vencimento = vencimento;
    }
    public void setStatus(Status status){
        this.status = status;
    }
}
