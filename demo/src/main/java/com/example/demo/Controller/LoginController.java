package com.Native.coder.Controller;

import java.util.List;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.*;

import com.Native.coder.Modelo.Login;
import com.Native.coder.Modelo.RegisterRequest;
import com.Native.coder.Modelo.RespostaUsuario;
import com.Native.coder.Modelo.Email;
import com.Native.coder.Modelo.Telefone;
import com.Native.coder.Modelo.World;
import com.Native.coder.Modelo.Perguntas;
import com.Native.coder.Repository.PerguntaRepository;
import com.Native.coder.Repository.RepostaUserRepository;
import com.Native.coder.Repository.WorldRepository;
import com.Native.coder.Servico.LoginServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.Native.coder.Modelo.Endereco;

import io.jsonwebtoken.InvalidClaimException;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "*")
public class LoginController {
    
    @Autowired
    private LoginServer loginServer;
    
    @Autowired
    
    private WorldRepository mundorepository;

    private   final  RepostaUserRepository   respostaUsuario  ;
    
    @Autowired
    
    private PerguntaRepository perguntas;


    LoginController(RepostaUserRepository respostaUsuario) {
        this.respostaUsuario = respostaUsuario;
    }


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
    @PostMapping("/respostauser")
    public ResponseEntity<String> processarRespostasUsuarios(@RequestBody List<RespostaUsuario> respostasUsuarioList) {
        try {
          
            if (respostasUsuarioList == null || respostasUsuarioList.isEmpty()) {
                return new ResponseEntity<>("A lista de respostas está vazia ou nula", HttpStatus.BAD_REQUEST);
            }

            boolean respostasCorretas = true; 
            for (RespostaUsuario resposta : respostasUsuarioList) {
                Long idPergunta = resposta.getIdpergunta().getIdpergunta();

                Perguntas perguntaBanco = perguntas.findById(idPergunta).orElse(null);
                if (perguntaBanco == null) {
                    return new ResponseEntity<>("Pergunta não encontrada para o ID fornecido", HttpStatus.BAD_REQUEST);
                }

         
                boolean correta = resposta.getRepostaAlgoritimoUser().equalsIgnoreCase(perguntaBanco.getRespostaAlgoritmo());
                String resultado = correta ? "V" : "F";
                resposta.setVouF(resultado);
                respostaUsuario.save(resposta);

        "
                if (!correta) {
                    respostasCorretas = false;
                }
            }

      
            if (respostasCorretas) {
                return new ResponseEntity<>("questão correta!", HttpStatus.OK); 
            } else {
                return new ResponseEntity<>("Resposta incorreta!", HttpStatus.OK);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erro ao processar as respostas: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }




}