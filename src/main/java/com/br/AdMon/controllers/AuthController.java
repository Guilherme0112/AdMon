package com.br.AdMon.controllers;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.Exceptions.EmailExistsException;
import com.br.AdMon.Exceptions.VerifyAuthException;
import com.br.AdMon.Util.JWTUtil;
import com.br.AdMon.Util.Util;
import com.br.AdMon.models.Usuarios;
import com.br.AdMon.service.ServiceAuth;
import com.br.AdMon.service.ServiceEmail;
import com.br.AdMon.service.ServiceUsuario;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthController {
    
    @Autowired
    private ServiceEmail emailService;

    @Autowired
    private ServiceUsuario usuarioService;

    @Autowired
    private ServiceAuth authService;

    @GetMapping("auth/login")
    public ModelAndView Login(HttpSession http){

        ModelAndView mv = new ModelAndView();

        if(Util.isAuth(http)){

            mv.setViewName("redirect:/");
            return mv;
        }

        mv.setViewName("auth/login");
        mv.addObject("usuarios", new Usuarios());

        return mv;
    }

    @PostMapping("auth/login")
    public ModelAndView LoginPOST(Usuarios usuarios, HttpSession http) throws NoSuchAlgorithmException, VerifyAuthException{

        ModelAndView mv = new ModelAndView();

        if(Util.isAuth(http)){

            mv.setViewName("redirect:/");
            return mv;
        }

        // Verifica as credenciais
        Usuarios loginUser = usuarioService.loginUsuario(usuarios.getEmail(), usuarios.getSenha());

        if(loginUser == null){

            mv.addObject("msg", "As credenciais estão incorretas");
            mv.addObject("usuarios", usuarios);
            mv.setViewName("auth/login");
            return mv;

        } 

        // Cria a sessão caso não entre no if
        http.setAttribute("session", loginUser);
        mv.setViewName("redirect:/dashboard");

        return mv;
    }

    @GetMapping("auth/register")
    public ModelAndView Registrar(HttpSession http){

        ModelAndView mv = new ModelAndView();

        if(Util.isAuth(http)){

            mv.setViewName("redirect:/");
        } 

        mv.addObject("usuarios", new Usuarios());

        return mv;
    }

    @PostMapping("auth/register")
    public ModelAndView RegisterPOST(@Valid Usuarios usuarios, BindingResult br, HttpSession http) throws Exception, EmailExistsException{

        ModelAndView mv = new ModelAndView();

        if(Util.isAuth(http)){

            mv.setViewName("redirect:/");
            return mv;
        } 

        try{

            if(br.hasErrors()){

                // Retorna para a view com os erros
                mv.addObject("usuarios", new Usuarios());
                mv.setViewName("auth/register");
                return mv;
            } 

            // Cria o token
            String token = JWTUtil.generateToken(usuarios.getEmail());
            
            usuarioService.salvarUsuario(usuarios);

            emailService.sendEmail(usuarios.getEmail(),
                        "Confirme sua conta",
                        "<a href='localhost:8080/verify-email/" + token + "'>Confirmar E-mail</a>");

            mv.setViewName("redirect:/sended-email");
            
        } catch (Exception e){

            mv.addObject("usuarios", new Usuarios());
            mv.addObject("erro_exception", e.getMessage());
            mv.setViewName("auth/register");
        }

        return mv;
    }

    @GetMapping("auth/logout")
    public String Logout(HttpSession session) throws Exception{

        try{

            authService.Logout(session);
        } catch (Exception e){

            throw new Exception("Erro: ", e);
        }
        
        return "redirect:/auth/login";
    }

}
