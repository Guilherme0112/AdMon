package com.br.AdMon.controllers;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.Exceptions.VerifyAuthException;
import com.br.AdMon.Util.Util;
import com.br.AdMon.models.Usuarios;
import com.br.AdMon.service.ServiceUsuario;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthController {
    
    @Autowired
    private ServiceUsuario usuarioService;

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
    public ModelAndView LoginPOST(Usuarios usuarios, HttpSession session) throws NoSuchAlgorithmException, VerifyAuthException{
        ModelAndView mv = new ModelAndView();


        // Verifica as credenciais
        Usuarios loginUser = usuarioService.loginUsuario(usuarios.getEmail(), Util.md5(usuarios.getSenha()));

        if(loginUser == null){
            mv.addObject("msg", "As credenciais estão incorretas");
            mv.addObject("usuarios", usuarios);
            mv.setViewName("auth/login");

        } else {
            session.setAttribute("session", loginUser);
            mv.setViewName("redirect:/dashboard");
        }

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

    public ModelAndView RegistroPOST(@Valid Usuarios usuario, BindingResult br) throws Exception{
        ModelAndView mv = new ModelAndView();

        if(br.hasErrors()){

            mv.addObject("usuarios", new Usuarios());
            mv.setViewName("auth/register");
        } else {

            usuarioService.salvarUsuario(usuario);
            mv.setViewName("redirect:/dashboard");
        }

        return mv;
    }

    @PostMapping("auth/register")
    public ModelAndView RegisterPOST(@Valid Usuarios usuarios, BindingResult br) throws Exception{
        ModelAndView mv = new ModelAndView();

        if(br.hasErrors()){

            mv.addObject("usuarios", new Usuarios());
            mv.setViewName("auth/register");
        } else {

            // Cria o registro da conta
            usuarioService.salvarUsuario(usuarios);
            mv.setViewName("redirect:/auth/login");
        }

        return mv;
    }

    @GetMapping("auth/logout")
    public String Logout(HttpSession session){
        session.invalidate();
        return "redirect:/auth/login";
    }

}
