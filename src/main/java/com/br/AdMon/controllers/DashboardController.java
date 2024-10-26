package com.br.AdMon.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.AdMon.dao.ContaDao;
import com.br.AdMon.models.Contas;

@RestController
public class DashboardController {

    @Autowired
    private ContaDao contarepositorio;

    @GetMapping("/dashboard-rest")
    public List<Contas> Dashboard(){
        List<Contas> contas = contarepositorio.findAll();
        return contas;
    }
}
