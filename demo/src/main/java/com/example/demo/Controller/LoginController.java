package com.Native.coder.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.*;

import com.Native.coder.Modelo.Login;
import com.Native.coder.Modelo.RegisterRequest;
import com.Native.coder.Modelo.Email;
import com.Native.coder.Modelo.Telefone;
import com.Native.coder.Modelo.World;
import com.Native.coder.Repository.WorldRepository;
import com.Native.coder.Servico.LoginServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.Native.coder.Modelo.Endereco;

import io.jsonwebtoken.InvalidClaimException;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class LoginController {
    
    @Autowired
    private LoginServer loginServer;
    
    @Autowired
    
    private WorldRepository mundorepository;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody Login loginRequest) {
        try {
      
            String token = loginServer.validarLogin(loginRequest.getUsers(), loginRequest.getSenha());
            if (token != null) {
                return ResponseEntity.ok(token); 
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas"); 
            }
        } catch (InvalidClaimException e) {
            System.out.print("Erro de autenticação: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        } catch (CannotGetJdbcConnectionException e) { 
            System.out.println("Erro de conexão com o banco de dados: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Erro ao acessar o banco de dados");
        } catch (Exception e) { 
            System.out.println("Erro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a solicitação");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        try {
            System.out.println("JSON Recebido: " + new ObjectMapper().writeValueAsString(request));

            if (request.getEndereco() == null) {
                System.out.println("Endereço está null");
                return new ResponseEntity<>("Endereço não pode ser nulo.", HttpStatus.BAD_REQUEST);
            } else {
                System.out.println("Endereço: " + request.getEndereco());
            }

            Endereco endereco = new Endereco();
            endereco.setBairro(request.getEndereco().getBairro());
            endereco.setCep(request.getEndereco().getCep());
            endereco.setCidade(request.getEndereco().getCidade());
            endereco.setEstado(request.getEndereco().getEstado());
            endereco.setLote(request.getEndereco().getLote());
            endereco.setQuadra(request.getEndereco().getQuadra());
            endereco.setRua(request.getEndereco().getRua());

            Login newUser = loginServer.criarLogin(
                request.getLogin().getUsers(),
                request.getLogin().getSenha(),
                request.getEmail().getEmailuser(),
                request.getTelefone().getTelefoneuser(),
                request.getLogin().getDatanasc(),
                request.getLogin().getSexo(),
                endereco
            );
            
   
            

            return new ResponseEntity<>("Cadastro bem-sucedido!", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erro ao criar o cadastro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    

    @PostMapping("/usuario/{userId}/mundo")
    public ResponseEntity<World> getMundo(@PathVariable Long userId) {
        try {
            World mundo = loginServer.obtermundo(userId);
            return ResponseEntity.ok(mundo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}



   

