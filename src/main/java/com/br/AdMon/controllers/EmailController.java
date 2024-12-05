package com.br.AdMon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmailController {

    @GetMapping("/verify-email")
    public ModelAndView VerifyEmail(){

        ModelAndView mv = new ModelAndView();

        mv.setViewName("mails/confirm-email");
        return mv;
    }
}
