package com.br.AdMon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home/index");
        return mv;
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("dashboard/dashboard");
        return mv;
    }
}
