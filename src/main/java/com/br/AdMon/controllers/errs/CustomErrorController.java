package com.br.AdMon.controllers.errs;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object statusCode = request.getAttribute("javax.servlet.error.status_code");
        String message = "Ocorreu um erro inesperado.";

        model.addAttribute("status", statusCode != null ? statusCode.toString() : "Desconhecido");
        model.addAttribute("error", "Erro ao processar a solicitação");
        model.addAttribute("message", message);

        return "others/error";
    }
}
