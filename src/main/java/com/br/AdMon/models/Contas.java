package com.br.AdMon.models;

import java.io.ObjectInputFilter.Status;
import java.time.LocalDate;

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
    private Integer id;
    
    @Column(name="conta")
    private String conta;
    
    @Column(name="valor")
    private Double valor;

    private String valorF;
    
    @Column(name="anotacao")
    private String anotacao;
    
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Column(name="vencimento")
    private LocalDate vencimento;

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
    public String getValorF(){
        return valorF;
    }
    public String getAnotacao(){
        return anotacao;
    }
    public Status getStatus(){
        return status;
    }
    public LocalDate getVencimento(){
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
    public void setValorF(String valorF){
        this.valorF = valorF;
    }
    public void setAnotacao(String anotacao){
        this.anotacao = anotacao;
    }
    public void setStatus(Status status){
        this.status = status;
    }
    public void setVencimento(LocalDate vencimento){
        this.vencimento = vencimento;
    }
}
