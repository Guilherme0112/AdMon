package com.br.AdMon.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.AdMon.Exceptions.CriptoException;
import com.br.AdMon.Exceptions.EmailExistsException;
import com.br.AdMon.Util.Util;
import com.br.AdMon.dao.UsuarioDao;
import com.br.AdMon.models.Usuarios;

@Service
public class ServiceUsuario {

    @Autowired
    private UsuarioDao usuarioRepository;

    public void salvarUsuario(Usuarios user) throws Exception{
        try {

            // Verifica se o E-mail já está em uso
            if(usuarioRepository.findByEmail(user.getEmail()) != null){
                throw new EmailExistsException("Este e-mail já está em uso");
            }

            // Criptografa a senha
            user.setSenha(Util.md5(user.getSenha()));
        } catch (NoSuchAlgorithmException e) {
            
            throw new CriptoException("Erro ao criptografar a senha");
        }

        usuarioRepository.save(user);
    }

}
