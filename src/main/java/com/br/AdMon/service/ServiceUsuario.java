package com.br.AdMon.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.AdMon.Util.Util;
import com.br.AdMon.dao.ContaDao;
import com.br.AdMon.dao.GanhoDao;
import com.br.AdMon.dao.UsuarioDao;
import com.br.AdMon.exceptions.ChangeNameException;
import com.br.AdMon.exceptions.ChangePasswordException;
import com.br.AdMon.exceptions.EmailExistsException;
import com.br.AdMon.exceptions.VerifyAuthException;
import com.br.AdMon.models.Usuarios;

@Service
public class ServiceUsuario {

    @Autowired
    private UsuarioDao usuarioRepository;

    @Autowired
    private GanhoDao ganhoRepository;

    @Autowired
    private ContaDao contaRepository;


    public void salvarUsuario(Usuarios user) throws Exception {

        try {

            // Verifica se o E-mail já está em uso
            if (usuarioRepository.findByUniqueEmail(user.getEmail()) != null) {
                throw new EmailExistsException("Este e-mail já está em uso");
            }

            // Verifica se a conta está inativa
            if(usuarioRepository.findByEmailInactive(user.getEmail()) != null){
                throw new EmailExistsException("Este e-mail já existe, mas está inativo");
            }

            // Criptografa a senha
            user.setSenha(Util.criptografar(user.getSenha()));

            usuarioRepository.save(user);

        } catch (EmailExistsException e) {

            System.out.println("Erro: " + e.getMessage());

        } catch (Exception e){

            System.out.println("Erro: " + e.getMessage());
        }
    }

    public Usuarios loginUsuario(String email, String senha) throws VerifyAuthException {

        Usuarios usuario = usuarioRepository.findByEmail(email);
        if(usuario == null){
            return null;
        }

        if(!Util.verificaSenha(senha, usuario.getSenha())){
            return null;
        }
        
        return usuario;
    }

    public void alterarSenha(String email, String novaSenha) throws ChangePasswordException {

        try {
            
            if (novaSenha.length() < 8) {
                throw new ChangePasswordException("A senha deve ter 8 ou mais caracteres");
            }

            Usuarios usuario = usuarioRepository.findByEmail(email);

            if (usuario == null) {
                throw new ChangePasswordException("Sua conta está inativa ou não está registada");
            }

            usuarioRepository.updatePassword(email, Util.criptografar(novaSenha));

        } catch (ChangePasswordException err) {

            throw new ChangePasswordException(err.getMessage());

        }
    }

    public void deletarUsuario(String email) throws Exception{

        try{
            // Deletar dados do usuário
            // Contas
            contaRepository.deleteByEmail(email);

            // Ganhos
            ganhoRepository.deleteByEmail(email);

            // Usuario
            usuarioRepository.deleteByEmail(email);


        } catch (Exception e){

            throw new Exception("Erro: ", e);
        }
    }

    public void alterarNome(String nome, String email, String senha) throws ChangeNameException{
        
        try{
        
            // Valida o nome
            if(nome.length() < 2 || nome.length() > 55){
                throw new ChangeNameException("O nome deve ter entre 2 e 55 caracteres");
            }

            Usuarios user = usuarioRepository.findByEmail(email);

            // Verifica se a senha está correta
            if(!Util.verificaSenha(senha, user.getSenha())){
                throw new ChangeNameException("A senha está incorreta");
            }

            usuarioRepository.updateName(email, nome);

        } catch (ChangeNameException e){
            
            throw new ChangeNameException(e.getMessage());

        }
    }
}
