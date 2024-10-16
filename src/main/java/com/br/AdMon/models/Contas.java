package com.br.AdMon.models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    private String valorF;
    
    @Column(name="anotacao")
    private String anotacao;
    
    @Column(name="status")
    private String status;
    
    @Column(name="vencimento")
    private LocalDate vencimento;

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
    // Este é o valor mostrado na view
    public String getValorF(){
        return valorF;
    }
    public String getAnotacao(){
        return anotacao;
    }
    public String getStatus(){
        return status;
    }
    public LocalDate getVencimento(){
        return vencimento;
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

    // Este é o valor mostrado na view
    public void setValorF(String valorF){
        this.valorF = valorF;
    }
    public void setAnotacao(String anotacao){
        this.anotacao = anotacao;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setVencimento(LocalDate vencimento){
        this.vencimento = vencimento;
    }
}
