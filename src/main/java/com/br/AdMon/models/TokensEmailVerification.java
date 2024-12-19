package com.br.AdMon.models;

import java.math.BigInteger;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "email_verification_tokens")
public class TokensEmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;
    private String token;
    private String userEmail;
    private LocalDateTime expire_in;

    public BigInteger getId(){
        return id;
    }
    public String getToken(){
        return token;
    }
    public String getUserEmail(){
        return userEmail;
    }
    public LocalDateTime getExpireIn(){
        return expire_in;
    }

    public void setId(BigInteger id){
        this.id = id;
    }
    public void setToken(String token){
        this.token = token;
    }
    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }
    public void setExpireIn(LocalDateTime expire_in){
        this.expire_in = expire_in;
    }
}
