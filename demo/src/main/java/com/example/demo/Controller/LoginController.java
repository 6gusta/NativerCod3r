package com.Native.coder.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Native.coder.Modelo.Login;
import com.Native.coder.Servico.LoginServer;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class LoginController {
    
    @Autowired
    private LoginServer loginServer;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody Login loginRequest) {
        try {
            
            String token = loginServer.validarLogin(loginRequest.getUsers(), loginRequest.getSenha());
            if (token != null) {
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas"); 
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a solicitação");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Login loginRequest) {
        try {
    
            Login newUser = loginServer.criarLogin(
                loginRequest.getUsers(),
                loginRequest.getSenha(),
                loginRequest.getEmail(),
                loginRequest.getTelefone(),
                loginRequest.getDatanasc(),
                loginRequest.getSexo(),
                loginRequest.getEndereco()
            );
            return new ResponseEntity<>("Cadastro bem-sucedido!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao criar o cadastro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
