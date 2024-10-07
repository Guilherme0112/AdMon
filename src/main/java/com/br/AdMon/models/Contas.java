package com.br.AdMon.models;

import java.io.ObjectInputFilter.Status;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Contas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(name="conta")
    private String conta;
    
    @Column(name="valor")
    private Double valor;
    
    @Column(name="anotacao")
    private String anotacao;
    
    @Column(name="status")
    private Status status;
    
    @Column(name="vencimento")
    private LocalDateTime vencimento;

    // Geters

    public Integer getId(){
        return id;
    }
    public String getConta(){
        return conta;
    }
    public Double getValor(){
        return valor;
    }
    public String getAnotacao(){
        return anotacao;
    }
    public Status getStatus(){
        return status;
    }
    public LocalDateTime vencimento(){
        return vencimento;
    }

    // Seters

    public void setId(Integer id){
        this.id = id;
    }
    public void setConta(String conta){
        this.conta = conta;
    }
    public void setValor(Double valor){
        this.valor = valor;
    }
    public void setAnotacao(String anotacao){
        this.anotacao = anotacao;
    }
    public void setStatus(Status status){
        this.status = status;
    }
    public void setVencimento(LocalDateTime vencimento){
        this.vencimento = vencimento;
    }
}
