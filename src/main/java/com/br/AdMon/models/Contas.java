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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="contas")
public class Contas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;
    
    @Column(name="conta")
    @NotBlank(message = "O nome da conta não pode ser nulo")
    @Size(min = 3, max = 30, message = "O nome da conta deve ter entre 3 e 30 caracteres")
    private String conta;
    
    @Column(name="valor")
    @Min(value = 1, message = "O valor deve ser maior que 0")
    @NotNull(message = "O valor da conta não pode ser nulo")
    private BigDecimal valor;
    
    @Column(name="anotacao")
    @Size(max = 200, message = "A anotação deve ter menos que 200 caracteres")
    private String anotacao;
    
    @Column(name="vencimento")
    @Future(message = "O vencimento deve estar no futuro")
    @NotNull(message = "O vencimento da conta não pode ser nulo")
    private LocalDate vencimento;

    @Email(message = "Este e-mail é inválido")
    private String userEmail;

    private String pago = "false";

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
    public String getUserEmail(){
        return userEmail;
    }
    public String getPago(){
        return pago;
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
    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }
    public void setPago(String pago){
        this.pago = pago;
    }
}
