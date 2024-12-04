package com.br.AdMon.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class ServiceAuth {

    public void Logout(HttpSession http){
        
        http.invalidate();
    }
}
