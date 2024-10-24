package com.br.AdMon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public ModelAndView Dashboard(){
        ModelAndView mv = new ModelAndView();
        
        mv.setViewName("dashboard/dashboard");
        return mv;
    }
}
