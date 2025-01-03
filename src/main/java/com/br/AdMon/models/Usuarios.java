package com.br.AdMon.models;

import java.math.BigInteger;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity
public class Usuarios {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Size(max = 55, min = 2, message = "O nome deve ter entre 2 a 55 caracteres")
    private String nome;
    
    @Email(message = "Este e-mail é inválido")
    private String email;
    
    @Size(min = 8, max = 16, message = "A senha deve ter entre 8 e 16 caracteres")
    private String senha;

    private Boolean ativo = false;

    // Getters
    public BigInteger getId(){
        return id;
    }
    public String getNome(){
        return nome;
    }
    public String getEmail(){
        return email;
    }
    public String getSenha(){
        return senha;
    }
    public Boolean getAtivo(){
        return ativo;
    }

    // Setters
    public void setId(BigInteger id){
        this.id = id;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
    public void setAtivo(Boolean ativo){
        this.ativo = ativo;
    }
}
