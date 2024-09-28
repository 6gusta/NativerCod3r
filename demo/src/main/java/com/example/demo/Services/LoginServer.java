package com.Native.coder.Servico;

import java.time.LocalDateTime;
import java.util.Date;
import javax.security.auth.login.AccountNotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Native.coder.Modelo.Login;
import com.Native.coder.Repository.userRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@Service
public class LoginServer {

    private final userRepository loginRepository;
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Chave segura
    private static final long EXPIRATION_TIME = 86400000;

    @Autowired
    public LoginServer(userRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public String validarLogin(String user, String senha) {
        try {
            Login login = loginRepository.findByUsers(user);
            if (login != null && BCrypt.checkpw(senha, login.getSenha())) {
            	  registrarLogin(user); 
                return GerarToken(user); 
            }
            return null;
        } catch (Exception e) {
            System.out.println("Erro: no método validarLogin " + e.getMessage());
            return null; 
        }
    }
    
    public Login criarLogin(String user, String senha, String email, String telefone, String datanasc, String sexo, String endereco) {
        if (loginRepository.findByEmail(email) != null) {
            throw new RuntimeException("Você já possui uma conta. Volte para a página inicial e faça seu login.");
        }
        try {
            Login login = new Login();
            login.setUsers(user);
            login.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
            login.setEmail(email);
            login.setTelefone(telefone);
            login.setDatanasc(datanasc);
            login.setSexo(sexo);
            login.setEndereco(endereco);
            return loginRepository.save(login);
        } catch (Exception e) {
            System.out.println("Erro: no método criarLogin " + e.getMessage());
            return null;
        }
    }
    
    public void registrarLogin(String users) throws AccountNotFoundException {
        System.out.println("Tentando registrar login para o usuário: " + users);
        Login login = loginRepository.findByUsers(users); 
        
        if (login != null) {
            System.out.println("Usuário encontrado: " + login.getUsers());
            
            
            int newLoginCount = login.getLoginCount() + 1;
            login.setLoginCount(newLoginCount);
            
        
            login.getLoginTimes().add(LocalDateTime.now());

          
            System.out.println("Antes de salvar: " + login);

   
            loginRepository.save(login);
            

            System.out.println("Após salvar: " + login);
        } else {
            System.out.println("Usuário não encontrado: " + users);
            throw new AccountNotFoundException("Usuário não encontrado");
        }
    }


    public String GerarToken(String user) {
        try {
            String token = Jwts.builder()
                    .setSubject(user)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SECRET_KEY)
                    .compact();
            System.out.println("Token gerado com sucesso: " + token);
            return token;
        } catch (Exception e) {
            System.err.println("Erro ao gerar token: " + e.getMessage());
            return null;
        }
    }
}
