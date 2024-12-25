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
import com.br.AdMon.dao.UsuarioDao;
import com.br.AdMon.exceptions.ChangePasswordException;
import com.br.AdMon.models.TokensForgotPassword;
import com.br.AdMon.service.ServiceUsuario;
import com.br.AdMon.service.mails.ServiceEmail;
import com.br.AdMon.service.mails.ServiceTokenEmailForgotPassword;

@Controller
public class ForgetPasswordController {

    @Autowired
    private TokenForgotPasswordDao tokensForgotPasswordRepository;

    @Autowired 
    private ServiceTokenEmailForgotPassword serviceTokenForgotPassword;

    @Autowired
    private ServiceEmail emailService;

    @Autowired 
    private UsuarioDao usuarioRepository;

    @Autowired 
    private ServiceUsuario usuarioService;

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
            emailService.sendEmail(email, "Redefinir Senha", "<html><h1>Link para redefinir senha</h1><a href='http://127.0.0.1:8080/change-password/" + token + "'>Redefinir Senha</a></html>");
            
            // Criando objeto para salvar no banco de dados
            TokensForgotPassword tokenForgotPasswordObject = new TokensForgotPassword();
            tokenForgotPasswordObject.setToken(token);
            tokenForgotPasswordObject.setUserEmail(email);
            tokenForgotPasswordObject.setExpireIn(LocalDateTime.now().plusMinutes(5));
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
            
            // Valida o token retornando 'true' caso esteja correto
            // Retorna uma Exception caso esteja errado
            serviceTokenForgotPassword.verifyToken(token);

            // Retorna a view com um valor boolean que vai renderizar a box de alterar a senha
            mv.setViewName("others/change-password");
            mv.addObject("change_password", true);

        } catch (Exception e) {
            mv.setViewName("others/change-password");
            mv.addObject("erro_exception", e.getMessage());
        }

        return mv;
    }

    @PostMapping("/change-password")
    public ModelAndView TrocarSenhaPOST(@RequestParam String token, @RequestParam String senha) throws Exception, ChangePasswordException{

        ModelAndView mv = new ModelAndView();

        try {

            // Verifica o token
            serviceTokenForgotPassword.verifyToken(token);

            // Busca os dados relacionados ao token
            TokensForgotPassword tokenObject = tokensForgotPasswordRepository.findByToken(token);
            
            // Busca o email
            String email = tokenObject.getUserEmail();

            // Verifica se o usuário está registrado ou ativo
            if(usuarioRepository.findByEmail(email) == null){ 
                tokensForgotPasswordRepository.delete(tokenObject);
                throw new ChangePasswordException("Sua conta está inativa ou não está registrada");
            }

            // Atualiza a senha do usuário
            usuarioService.alterarSenha(email, senha);

            // Redireciona para a tela de login
            mv.setViewName("redirect:/auth/login");
            
        } catch (ChangePasswordException e){

            // "erro_vali" trás o erro da validação
            // "token" manda o token do pedido para trocar a senha
            // "change_password" manda um true para renderizar o formulário
            mv.addObject("erro_vali", e.getMessage());
            mv.addObject("token", token);
            mv.addObject("change_password", true);
            mv.setViewName("others/change-password");

        } catch (Exception e) {
            mv.addObject("erro_exception", e.getMessage());
            mv.setViewName("others/change-password");
        }

        return mv;
    }
}
