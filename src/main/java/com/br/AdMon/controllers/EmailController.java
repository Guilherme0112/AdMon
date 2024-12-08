package com.br.AdMon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.Util.JWTUtil;

@Controller
public class EmailController {

    @GetMapping("/verify-email/{token}")
    public ModelAndView VerifyEmail(@PathVariable("token") String token){

        ModelAndView mv = new ModelAndView();

        try {
            
            if(JWTUtil.extractData(token) != null){
                
            }

        } catch (Exception e) {
            System.out.println("Erro:" + e);
        }

        mv.setViewName("mails/confirm-email");
        return mv;
    }
}
