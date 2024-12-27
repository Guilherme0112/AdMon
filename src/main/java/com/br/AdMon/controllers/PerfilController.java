package com.br.AdMon.controllers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.Util.Util;
import com.br.AdMon.dao.UsuarioDao;
import com.br.AdMon.exceptions.ChangeNameException;
import com.br.AdMon.exceptions.ChangePasswordException;
import com.br.AdMon.models.Usuarios;
import com.br.AdMon.service.ServiceAuth;
import com.br.AdMon.service.ServiceUsuario;

import jakarta.servlet.http.HttpSession;

@Controller
public class PerfilController {

    @Autowired
    private ServiceUsuario usuarioService;

    @Autowired
    private UsuarioDao usuarioRepository;

    @Autowired
    private ServiceAuth authService;

    @GetMapping("/perfil/editar")
    public ModelAndView PerfilSenha(HttpSession http){

        ModelAndView mv = new ModelAndView();

        if(!Util.isAuth(http)){

            mv.setViewName("redirect:/auth/login");
            return mv;
        }

        Usuarios session = (Usuarios) http.getAttribute("session");
        Usuarios user = usuarioRepository.findByEmail(session.getEmail());

        mv.addObject("nome", user.getNome());
        mv.setViewName("perfil/perfil");

        return mv;
    }

    @PostMapping("/senha/editar")
    public ModelAndView PerfilSenhaPost(@RequestParam String senhaAntiga, @RequestParam String senhaNova, HttpSession http) throws ChangePasswordException, Exception{

        ModelAndView mv = new ModelAndView();

        if(!Util.isAuth(http)){

            mv.setViewName("redirect:/auth/login");
            return mv;
        }

        try{

            // Busca o usuario da sessão
            Usuarios session = (Usuarios) http.getAttribute("session");
            Usuarios user = usuarioRepository.findByEmail(session.getEmail());

            // Verifica se a senha corresponde a do banco
            if(!Util.verificaSenha(senhaAntiga, user.getSenha())){
                throw new ChangePasswordException("A senha está incorreta");
            }
            
            // Altera a senha no banco de dados
            usuarioService.alterarSenha(session.getEmail(), senhaNova);
            mv.addObject("msgSucess", "A senha foi trocada com sucesso");
            mv.setViewName("perfil/perfil");
            
        } catch (ChangePasswordException err){

            mv.addObject("msgError", err.getMessage());
            mv.setViewName("perfil/perfil");

        } catch (Exception err){

            System.out.println(err.getMessage());
        }

        return mv;
    }

    @PostMapping("/nome/editar")
    public ModelAndView TrocarNome(@RequestParam String nome, @RequestParam String senha, HttpSession http) throws ChangeNameException{

        ModelAndView mv = new ModelAndView();

        if(!Util.isAuth(http)){

            mv.setViewName("redirect:/auth/login");
            return mv;
        }

        try{

            Usuarios session = (Usuarios) http.getAttribute("session");
            usuarioService.alterarNome(nome, session.getEmail(), senha);
            mv.setViewName("redirect:/perfil/editar");

        } catch (ChangeNameException e) {

            mv.addObject("msgNameError", e.getMessage());
            mv.setViewName("perfil/perfil");
        }
        
        return mv;
    }

    @PostMapping("/perfil/deletar")
    public ResponseEntity<Map<String, String>> deletarConta(@RequestBody Map<String, Boolean> body, HttpSession http) throws Exception{

        if(!Util.isAuth(http)){

            return ResponseEntity.ok(Map.of("redirect", "/auth/login"));
        }

        Boolean del = body.get("del");

        if(Boolean.TRUE.equals(del)){

            try {

                Usuarios session = (Usuarios) http.getAttribute("session");
                usuarioService.deletarUsuario(session.getEmail());
                authService.Logout(http);

            } catch (Exception e) {
                
                throw new Exception("Erro: ", e);
            }
                
        }

        return ResponseEntity.ok(Map.of("redirect", "/auth/login"));
    }

}
