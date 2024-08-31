package com.Native.coder.Servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Native.coder.Modelo.Login;
import com.Native.coder.Repository.userRepository;

@Service
public class LoginServer {

    @Autowired
    public userRepository loginRepository;

    public boolean validarLogin(String user, String senha) {
        try {
            Login login = loginRepository.findByUsers(user);
            if (login != null) {
                return senha.equals(login.getSenha());
            }
            return false;
        } catch (Exception e) {
            System.out.println("Erro: no metodo validar login  " + e.getMessage());
            return false;
        }
    }


    public Login criarLogin(String user, String senha, String email) {
        if (loginRepository.findByEmail(email) != null) { 
            throw new RuntimeException("Você já possui uma conta. Volte para a página inicial e faça seu login.");
        }
        try {
            Login login = new Login();
            login.setUsers(user);
            login.setSenha(senha);
            login.setEmail(email);
            return loginRepository.save(login);
        } catch (Exception e) {
            System.out.println("Erro: no método criar " + e.getMessage());
            return null; 
        }
    }
    
}
