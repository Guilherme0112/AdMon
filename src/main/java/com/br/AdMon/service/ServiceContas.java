package com.br.AdMon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.AdMon.dao.ContaDao;
import com.br.AdMon.models.Contas;


@Service
public class ServiceContas {

    @Autowired
    private ContaDao contaRepository;

    // Alterna o status entre false e true
    public void alternarStatus(String email, Integer id){

        List<Contas> queryConta = contaRepository.findByEmailAndId(email, id);

        if(!queryConta.isEmpty()){

            Contas conta = queryConta.get(0);

            String status = conta.getPago();

            if ("false".equals(status)) {
                contaRepository.updateStatusByEmail("true", email, id);
            } else {
                contaRepository.updateStatusByEmail("false", email, id);
            }
        }
    }
}
