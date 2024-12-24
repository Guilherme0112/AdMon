package com.br.AdMon.controllers.others;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.br.AdMon.Util.Util;
import com.br.AdMon.dao.TokenForgotPasswordDao;
import com.br.AdMon.models.TokensForgotPassword;
import com.br.AdMon.service.mails.ServiceEmail;

@Controller
public class ForgetPasswordController {

    @Autowired
    private TokenForgotPasswordDao tokensForgotPasswordRepository;

    @Autowired
    private ServiceEmail emailService;

    @GetMapping("/forgot-password")
    public ModelAndView ForgotPassword(){
        
        ModelAndView mv = new ModelAndView();
        
        mv.setViewName("others/forgot-password");
        return mv;
    }

    @PostMapping("/forgot-password")
    public ModelAndView ForgotPasswordPOST(@RequestParam String email) throws Exception{

        ModelAndView mv = new ModelAndView();

        try {
        
            String token = Util.generateToken();
            emailService.sendEmail(email, "Esqueci minha senha", "<html><a href='127.0.0.1:8080/change-password/" + token + "'>Redefinir Senha</a></html>");
            
            // Criando objeto para salvar no banco de dados
            TokensForgotPassword tokenForgotPasswordObject = new TokensForgotPassword();
            tokenForgotPasswordObject.setToken(token);
            tokenForgotPasswordObject.setUserEmail(email);
            tokenForgotPasswordObject.setExpireIn(LocalDateTime.now().minusMinutes(5));
            tokensForgotPasswordRepository.save(tokenForgotPasswordObject);

            mv.setViewName("mails/sended-email");

        } catch (Exception e) {
            mv.setViewName("others/forgot-password");
            mv.addObject("erro_exception", e.getMessage());
        }

        return mv;
    }

    @GetMapping("/change-password/{tokenForgotPassword}")
    public ModelAndView TrocarSenha(@PathVariable("tokenForgotPassword") String token) throws Exception{

        ModelAndView mv = new ModelAndView();

        try {
            
            if(tokensForgotPasswordRepository.findByToken(token) == null){
                tokensForgotPasswordRepository.delete(tokensForgotPasswordRepository.findByToken(token));
                throw new Exception("Token inválido");
            }

            // Recupera dados que o token tem
            TokensForgotPassword tokenObject = tokensForgotPasswordRepository.findByToken(token);

            // Verifica se o token é válido
            if(tokenObject.getExpireIn().isBefore(LocalDateTime.now())){
                tokensForgotPasswordRepository.delete(tokenObject);
                throw new Exception("Token expirado");
            }

            // Retorna a view com um valor boolean que vai renderizar a box de alterar a senha
            mv.setViewName("others/change-password");
            mv.addObject("change_password", true);

        } catch (Exception e) {
            mv.setViewName("others/change-password");
            mv.addObject("erro_exception", e.getMessage());
        }

        return mv;
    }
}
