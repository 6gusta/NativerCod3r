package com.Native.coder.Servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Native.coder.Modelo.Cadastro;
import com.Native.coder.Modelo.Login;
import com.Native.coder.Repository.UserRepository; // Repository for Cadastro
import com.Native.coder.Repository.LoginRepository; // Repository for Login

@Service
public class LoginServer {

    @Autowired
    private UserRepository cadastroRepository; 

    @Autowired
    private LoginRepository loginRepository;

    public boolean validarLogin(String username, String senha) {
        try {
            Login login = loginRepository.findByUsername(username);
            if (login != null) {
                return senha.equals(login.getSenha()); // Comparar senha em texto puro
            }
            return false;
        } catch (Exception e) {
            System.out.println("Erro no método validarLogin: " + e.getMessage());
            return false;
        }
    }

    public Cadastro registrarUsuario(String username, String senha, String email, String telefone, String dataDeNasc, String endereco, String sexo) {
        if (loginRepository.findByUsername(username) != null) {
            throw new RuntimeException("Usuário já existe. Tente fazer login.");
        }

        if (loginRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email já está registrado. Use outro email.");
        }

        try {
            Cadastro cadastro = new Cadastro();
            cadastro.setEmail(email);
            cadastro.setTelefone(telefone);
            cadastro.setDataDeNasc(dataDeNasc);
            cadastro.setEndereco(endereco);
            cadastro.setSexo(sexo);

            Cadastro novoCadastro = cadastroRepository.save(cadastro);

            Login login = new Login();
            login.setUsername(username);
            login.setSenha(senha); // Senha em texto puro
            login.setCadastro(novoCadastro);

            loginRepository.save(login);

            return novoCadastro;
        } catch (Exception e) {
            System.out.println("Erro no método registrarUsuario: " + e.getMessage());
            return null;
        }
    }
}
