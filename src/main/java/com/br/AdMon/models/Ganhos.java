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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="ganhos")
public class Ganhos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column(name="ganho")
    @NotNull(message="Este ganho não pode ser nulo")
    @Size(min = 3, max = 30, message = "O campo deve ter entre 3 e 30 caracteres")
    private String ganho;

    @Column(name="anotacao")
    @Size(max = 200, message = "A anotação deve ter no máximo 200 caracteres")
    private String anotacao;

    @Column(name="valor")
    @NotNull(message = "O valor não pode ser nulo")
    @Min(value = 1, message = "O valor deve ser maior que 0")
    private BigDecimal valor;

    @Email(message = "Este e-mail é inválido")
    private String userEmail;

    private Boolean esteMes;

    @Column(updatable = false)
    private LocalDate criado = LocalDate.now();
    
  // Getters

    public BigInteger getId(){
        return id;
    }
    public String getGanho(){
        return ganho;
    }
    public String getAnotacao(){
        return anotacao;
    }
    public BigDecimal getValor(){
        return valor;
    }
    public String getUserEmail(){
        return userEmail;
    }
    public Boolean getEsteMes(){
        return esteMes;
    }
    public LocalDate getCriado(){
        return criado;
    }

    // Setters

    public void setId(BigInteger id){
        this.id = id;
    }
    public void setGanho(String ganho){
        this.ganho = ganho;
    }
    public void setAnotacao(String anotacao){
        this.anotacao = anotacao;
    }
    public void setValor(BigDecimal valor){
        this.valor = valor;
    }
    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }
    public void setEsteMes(Boolean esteMes){
        this.esteMes = esteMes;
    }
}
