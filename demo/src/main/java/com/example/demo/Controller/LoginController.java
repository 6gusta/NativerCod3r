package com.Native.coder.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Native.coder.Modelo.Login;
import com.Native.coder.Modelo.Cadastro;
import com.Native.coder.Servico.LoginServer;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://127.0.0.1:5500")  
public class LoginController {

    @Autowired
    private LoginServer loginServer;

    // Método para login
    @PostMapping
    public ResponseEntity<String> login(@RequestBody Cadastro loginRequest) {
        try {
            boolean loginValido = loginServer.validarLogin(loginRequest.getUsername(), loginRequest.getSenha());

            if (loginValido) {
                return new ResponseEntity<>("Login bem-sucedido!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Usuário ou senha inválidos.", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erro no login: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para registro
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Cadastro registerRequest) {
        try {
            Cadastro newUser = loginServer.registrarUsuario(
                registerRequest.getUsername(),
                registerRequest.getSenha(),
                registerRequest.getEmail(),
                registerRequest.getTelefone(),
                registerRequest.getDataDeNasc(),
                registerRequest.getEndereco(),
                registerRequest.getSexo()
            );
            return new ResponseEntity<>("Cadastro bem-sucedido!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao criar o cadastro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
